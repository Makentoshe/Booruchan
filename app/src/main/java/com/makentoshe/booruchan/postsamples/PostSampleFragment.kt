package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.PostSampleFragmentViewModelFactory
import com.makentoshe.booruchan.PostsRepository
import com.makentoshe.booruchan.postsamples.view.PostSampleFragmentUi
import org.jetbrains.anko.AnkoContext

class PostSampleFragment : Fragment() {

    private lateinit var viewModel: PostsSampleFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = buildViewModel(arguments!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.update()
        return PostSampleFragmentUi(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

    private fun buildViewModel(arguments: Bundle): PostsSampleFragmentViewModel {
        val booru = arguments.getSerializable(Booru::class.java.simpleName) as Booru
        val startPosition = arguments.getInt(Int::class.java.simpleName)
        val postsRepository = arguments.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository
        val sampleRepository = arguments.getSerializable(ImageRepository::class.java.simpleName) as ImageRepository

        val factory = PostSampleFragmentViewModelFactory(booru, startPosition, postsRepository, sampleRepository)
        return ViewModelProviders.of(this, factory)[PostsSampleFragmentViewModel::class.java]
    }

}

