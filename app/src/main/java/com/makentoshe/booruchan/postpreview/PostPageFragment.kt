package com.makentoshe.booruchan.postpreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Fragment
import com.makentoshe.booruchan.postpreview.view.PostPageFragmentUi
import com.makentoshe.repository.Repository
import com.makentoshe.repository.SampleImageRepository
import org.jetbrains.anko.AnkoContext

class PostPageFragment : androidx.fragment.app.Fragment() {

    private var position = -1
    private lateinit var viewModel: PostPageFragmentViewModel
    private lateinit var postsDownloadViewModel: PostsDownloadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        position = arguments!!.getInt(Int::class.java.simpleName)
        val arguments = Companion.arguments[position]!!

        var factory: ViewModelProvider.NewInstanceFactory = PostPageFragmentViewModel.Factory(
            arguments.booru,
            arguments.postsRepository,
            arguments.previewsRepository,
            arguments.samplesRepository
        )

        viewModel = ViewModelProviders.of(this, factory)[PostPageFragmentViewModel::class.java]

        factory = PostsDownloadViewModel.Factory(arguments.postsRepository, arguments.tags, position)
        postsDownloadViewModel = ViewModelProviders.of(this, factory)[PostsDownloadViewModel::class.java]

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.onCreateView(this)
        postsDownloadViewModel.onCreateView(this)

        return PostPageFragmentUi(viewModel, postsDownloadViewModel)
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
        val postsRepository: Repository<Booru.PostRequest, Posts>,
        val previewsRepository: Repository<String, ByteArray>,
        val samplesRepository: SampleImageRepository
    )
}