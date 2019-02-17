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
import com.makentoshe.booruchan.PostInternalCache
import com.makentoshe.booruchan.PreviewsInternalCache
import com.makentoshe.booruchan.postpreview.view.PostPageFragmentUi
import com.makentoshe.repository.*
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

class PostPageFragment : androidx.fragment.app.Fragment() {

    private var position = -1
    private lateinit var postsDownloadViewModel: PostsDownloadViewModel
    private lateinit var adapterViewModel: AdapterViewModel
    private lateinit var navigatorViewModel: NavigatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        position = arguments!!.getInt(POSITION)
        val booru = arguments!!.get(BOORU) as Booru
        val tags = arguments!!.get(TAGS) as Set<Tag>

        val previewsRepository = CachedRepository<Post, ByteArray>(
            PreviewsInternalCache(requireContext(), "previews"),
            PreviewImageRepository(booru)
        )

        val postsRepository = CachedRepository(
            PostInternalCache(requireContext(), "posts"),
            PostsRepository(booru)
        )

        var factory: ViewModelProvider.NewInstanceFactory =
            PostsDownloadViewModel.Factory(postsRepository, tags, position)
        postsDownloadViewModel = ViewModelProviders.of(this, factory)[PostsDownloadViewModel::class.java]

        factory = AdapterViewModel.Factory(previewsRepository)
        adapterViewModel = ViewModelProviders.of(this, factory)[AdapterViewModel::class.java]

        factory = NavigatorViewModel.Factory(position, Booruchan.INSTANCE.router)
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

    companion object {
        private const val POSITION = "Position"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"

        fun create(position: Int, booru: Booru, tags: Set<Tag>): androidx.fragment.app.Fragment {

            return PostPageFragment().apply {
                this.arguments = Bundle().apply {
                    putInt(POSITION, position)
                    putSerializable(BOORU, booru)
                    putSerializable(TAGS, tags as Serializable)
                }
            }
        }
    }
}