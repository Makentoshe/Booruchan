package com.makentoshe.booruchan.postsamplespage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.PostSamplePageFragmentViewModelFactory
import com.makentoshe.booruchan.ViewModelFragment
import com.makentoshe.booruchan.postsamples.model.SamplePageHorizontalScrollBlockController
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.PostsRepository
import org.jetbrains.anko.AnkoContext

class PostSamplePageFragment : ViewModelFragment<PostSamplePageViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return PostSamplePageFragmentUi(viewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun buildViewModel(arguments: Bundle): PostSamplePageViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)
        val blockController =
            arguments.getSerializable(SamplePageHorizontalScrollBlockController::class.java.simpleName) as SamplePageHorizontalScrollBlockController
        val sampleRepository = arguments.getSerializable(ImageRepository::class.java.simpleName) as ImageRepository
        val postsRepository = arguments.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository

        val factory =
            PostSamplePageFragmentViewModelFactory(position, blockController, sampleRepository, postsRepository)
        return ViewModelProviders.of(this, factory)[PostSamplePageViewModel::class.java]
    }
}

