package com.makentoshe.booruchan.postsamples.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.AppActivity
import com.makentoshe.booruchan.Fragment
import com.makentoshe.booruchan.postsamples.PostsSampleFragmentViewModel
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.PostsRepository
import org.jetbrains.anko.AnkoContext

class PostSampleFragment : Fragment<PostsSampleFragmentViewModel>() {

    override val argumentInitializer: String
        get() = PostSampleFragment::class.java.simpleName

    override fun clearArguments(arguments: Bundle?): Bundle? {
        return Bundle().apply {
            putInt(Int::class.java.simpleName, arguments!!.getInt(Int::class.java.simpleName))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return PostSampleFragmentUi(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

    override fun buildViewModel(arguments: Bundle): PostsSampleFragmentViewModel {
        val booru = arguments.getSerializable(Booru::class.java.simpleName) as Booru
        val startPosition = arguments.getInt(Int::class.java.simpleName)
        val postsRepository = arguments.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository
        val sampleRepository = arguments.getSerializable(ImageRepository::class.java.simpleName) as ImageRepository
        val requestPermissionController = (requireActivity() as AppActivity).requestPermissionController

        val factory = PostsSampleFragmentViewModel.Factory(
            booru,
            startPosition,
            postsRepository,
            sampleRepository,
            requestPermissionController
        )
        return ViewModelProviders.of(this, factory)[PostsSampleFragmentViewModel::class.java]
    }

}

