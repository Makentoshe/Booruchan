package com.makentoshe.booruchan.postsamplespageimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.*
import org.jetbrains.anko.AnkoContext

class PostSamplePageImageFragment : Fragment() {

    private lateinit var viewModel: PostSamplePagePreviewFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = buildViewModel(arguments!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostSamplePageImageFragmentUi(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

    private fun buildViewModel(arguments: Bundle): PostSamplePagePreviewFragmentViewModel {
        val sampleRepository = arguments.getSerializable(ImageRepository::class.java.simpleName) as ImageRepository
        val position = arguments.getInt(Int::class.java.simpleName)
        val postsRepository = arguments.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository

        val factory = PostSamplePageImageFragmentViewModelFactory(sampleRepository, position, postsRepository)
        return ViewModelProviders.of(this, factory)[PostSamplePagePreviewFragmentViewModel::class.java]
    }
}

