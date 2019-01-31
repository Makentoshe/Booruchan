package com.makentoshe.booruchan.postsamplespage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.ImageRepository
import com.makentoshe.booruchan.PostSamplePageFragmentViewModelFactory
import com.makentoshe.booruchan.PostsRepository
import com.makentoshe.booruchan.postsamples.model.SamplePageBlockController
import org.jetbrains.anko.AnkoContext

class PostSamplePageFragment : Fragment() {

    private lateinit var viewModel: PostSamplePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = buildViewModel(arguments!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return PostSamplePageFragmentUi(viewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }

    private fun buildViewModel(arguments: Bundle): PostSamplePageViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)
        val blockController =
            arguments.getSerializable(SamplePageBlockController::class.java.simpleName) as SamplePageBlockController
        val sampleRepository = arguments.getSerializable(ImageRepository::class.java.simpleName) as ImageRepository
        val postsRepository = arguments.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository

        val factory =
            PostSamplePageFragmentViewModelFactory(position, blockController, sampleRepository, postsRepository)
        return ViewModelProviders.of(this, factory)[PostSamplePageViewModel::class.java]
    }
}

