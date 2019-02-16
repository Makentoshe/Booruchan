package com.makentoshe.booruchan.postpreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.PreviewsInternalCache
import com.makentoshe.booruchan.postpreview.view.PostPageFragmentUi
import com.makentoshe.repository.*
import org.jetbrains.anko.AnkoContext

class PostPageFragment : androidx.fragment.app.Fragment() {

    private var position = -1
    private lateinit var postsDownloadViewModel: PostsDownloadViewModel
    private lateinit var adapterViewModel: AdapterViewModel
    private lateinit var navigatorViewModel: NavigatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        position = arguments!!.getInt(Int::class.java.simpleName)
        val arguments = Companion.arguments[position]!!

        val previewsRepository = CachedRepository<Post, ByteArray>(
            PreviewsInternalCache(requireContext(), "previews"),
            PreviewImageRepository(arguments.booru)
        )

        var factory: ViewModelProvider.NewInstanceFactory =
            PostsDownloadViewModel.Factory(arguments.postsRepository, arguments.tags, position)
        postsDownloadViewModel = ViewModelProviders.of(this, factory)[PostsDownloadViewModel::class.java]

        factory = AdapterViewModel.Factory(previewsRepository)
        adapterViewModel = ViewModelProviders.of(this, factory)[AdapterViewModel::class.java]

        factory = NavigatorViewModel.Factory(
            arguments.postsRepository,
            12,
            position,
            Booruchan.INSTANCE.router
        )
        navigatorViewModel = ViewModelProviders.of(this, factory)[NavigatorViewModel::class.java]

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        postsDownloadViewModel.onCreateView(this)
        adapterViewModel.onCreateView(this)
        navigatorViewModel.onCreateView(this)

        return PostPageFragmentUi(postsDownloadViewModel, adapterViewModel, navigatorViewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onDestroy() {
        super.onDestroy()

        val activity = activity
        val isChangingConfigurations = activity != null && activity.isChangingConfigurations
        if (!isChangingConfigurations) {
            Companion.arguments.remove(position)
        }
    }

    companion object {
        fun create(position: Int, arguments: Arguments): androidx.fragment.app.Fragment {

            Companion.arguments[position] = arguments

            return PostPageFragment().apply {
                this.arguments = Bundle().apply {
                    putInt(Int::class.java.simpleName, position)
                }
            }
        }

        private val arguments = HashMap<Int, Arguments>()
    }

    data class Arguments(
        val booru: Booru,
        val tags: Set<Tag>,
        val postsRepository: Repository<Booru.PostRequest, Posts>
    )
}