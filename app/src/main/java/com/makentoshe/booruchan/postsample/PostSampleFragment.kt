package com.makentoshe.booruchan.postsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.ImageInternalCache
import com.makentoshe.booruchan.PostInternalCache
import com.makentoshe.booruchan.postsample.view.PostSampleUi
import com.makentoshe.repository.CachedRepository
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

class PostSampleFragment : Fragment() {

    private lateinit var viewModel: PostSampleViewModel
    private lateinit var downloadViewModel: DownloadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val position = arguments!!.getInt(POSITION)
        val booru = arguments!!.get(BOORU) as Booru
        val tags = arguments!!.get(TAGS) as Set<Tag>

        val postsCache = PostInternalCache(requireContext(), "posts")
        val postsRepository = CachedRepository(postsCache, PostsRepository(booru))

        val imageInternalCache = ImageInternalCache(requireContext(), "samples")
        val samplesRepository = CachedRepository(imageInternalCache, SampleImageRepository(booru))

        var factory: ViewModelProvider.NewInstanceFactory =
            PostSampleViewModel.Factory(position, postsRepository, samplesRepository)
        viewModel = ViewModelProviders.of(this, factory)[PostSampleViewModel::class.java]

        factory = DownloadViewModel.Factory(postsRepository, tags, samplesRepository, position)
        downloadViewModel = ViewModelProviders.of(this, factory)[DownloadViewModel::class.java]

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSampleUi(viewModel, downloadViewModel, downloadViewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }

    companion object {
        private const val POSITION = "Position"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun create(
            position: Int,
            booru: Booru,
            tags: Set<Tag>
        ): androidx.fragment.app.Fragment {
            return PostSampleFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                    putSerializable(BOORU, booru)
                    putSerializable(TAGS, tags as Serializable)
                }
            }
        }
    }
}