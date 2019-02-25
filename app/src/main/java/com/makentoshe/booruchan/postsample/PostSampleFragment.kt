package com.makentoshe.booruchan.postsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.ImageInternalCache
import com.makentoshe.booruchan.PostInternalCache
import com.makentoshe.booruchan.postsample.view.PostSampleUi
import com.makentoshe.repository.CachedRepository
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import java.io.Serializable
import kotlin.random.Random

class PostSampleFragment : Fragment() {

    private lateinit var downloadViewModel: DownloadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val position = arguments!!.getInt(POSITION)
        val booru = arguments!!.get(BOORU) as Booru
        val tags = arguments!!.get(TAGS) as Set<Tag>

        val postsCache = PostInternalCache(requireContext(), "posts")
        val postsRepository = CachedRepository(postsCache, PostsRepository(booru))

        val imageInternalCache = ImageInternalCache(requireContext(), "samples")
        val samplesRepository = CachedRepository(imageInternalCache, SampleImageRepository(booru))

//        val factory = DownloadViewModel.Factory(postsRepository, tags, samplesRepository, position)
//        downloadViewModel = ViewModelProviders.of(this, factory)[DownloadViewModel::class.java]
//
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        downloadViewModel.onCreateView(this)

        return View(context).apply {
            backgroundColor = Random.nextInt()
        }
//        return PostSampleUi(downloadViewModel, downloadViewModel)
//            .createView(AnkoContext.create(requireContext(), this))
    }

    companion object {
        private const val POSITION = "Position"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun create(position: Int, booru: Booru, tags: Set<Tag>) = PostSampleFragment().apply {
            arguments = Bundle().apply {
                putInt(POSITION, position)
                putSerializable(BOORU, booru)
                putSerializable(TAGS, tags as Serializable)
            }
        }
    }
}