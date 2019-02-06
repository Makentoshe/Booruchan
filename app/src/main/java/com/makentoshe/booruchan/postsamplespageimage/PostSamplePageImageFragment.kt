package com.makentoshe.booruchan.postsamplespageimage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.Fragment
import com.makentoshe.booruchan.postsamplespageimage.view.PostSamplePageImageFragmentUi
import com.makentoshe.repository.ImageRepository
import com.makentoshe.repository.PostsRepository
import org.jetbrains.anko.AnkoContext

class PostSamplePageImageFragment : Fragment<PostSamplePageImageFragmentViewModel>() {

    override val argumentInitializer: String
        get() = PostSamplePageImageFragment::class.java.simpleName.plus(arguments!!.getInt(Int::class.java.simpleName))


    override fun clearArguments(arguments: Bundle?): Bundle? {
        return Bundle().apply {
            putInt(Int::class.java.simpleName, arguments!!.getInt(Int::class.java.simpleName))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return PostSamplePageImageFragmentUi(viewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun buildViewModel(arguments: Bundle): PostSamplePageImageFragmentViewModel {
        val sampleRepository = arguments.getSerializable(ImageRepository::class.java.simpleName) as ImageRepository
        val position = arguments.getInt(Int::class.java.simpleName)
        val postsRepository = arguments.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository

        val factory = PostSamplePageImageFragmentViewModel.Factory(position, postsRepository, sampleRepository)
        return ViewModelProviders.of(this, factory)[PostSamplePageImageFragmentViewModel::class.java]
    }
}

