package com.makentoshe.booruchan.postsamplespageimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.postsamples.model.SamplePageBlockController
import com.makentoshe.booruchan.postsamplespageimage.view.PostSamplePageImageFragmentUi
import org.jetbrains.anko.AnkoContext

class PostSamplePageImageFragment : ViewModelFragment<PostSamplePageImageFragmentViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSamplePageImageFragmentUi(viewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun buildViewModel(arguments: Bundle): PostSamplePageImageFragmentViewModel {
        val sampleRepository = arguments.getSerializable(ImageRepository::class.java.simpleName) as ImageRepository
        val position = arguments.getInt(Int::class.java.simpleName)
        val postsRepository = arguments.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository
        val samplePageBlockController =
            arguments.getSerializable(SamplePageBlockController::class.java.simpleName) as SamplePageBlockController

        val factory = PostSamplePageImageFragmentViewModelFactory(
            sampleRepository,
            position,
            postsRepository,
            samplePageBlockController
        )
        return ViewModelProviders.of(this, factory)[PostSamplePageImageFragmentViewModel::class.java]
    }
}

