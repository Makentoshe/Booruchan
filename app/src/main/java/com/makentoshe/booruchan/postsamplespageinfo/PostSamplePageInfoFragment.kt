package com.makentoshe.booruchan.postsamplespageinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.PostSamplePageInfoFragmentViewModelFactory
import com.makentoshe.booruchan.ViewModelFragment
import com.makentoshe.repository.PostsRepository
import org.jetbrains.anko.AnkoContext


class PostSamplePageInfoFragment : ViewModelFragment<PostSamplePageInfoFragmentViewModel>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return PostSamplePageInfoFragmentUi(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

    override fun buildViewModel(arguments: Bundle): PostSamplePageInfoFragmentViewModel {
        val position = arguments.getInt(Int::class.java.simpleName)
        val postsRepository = arguments.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository

        val factory = PostSamplePageInfoFragmentViewModelFactory(position, postsRepository)
        return ViewModelProviders.of(this, factory)[PostSamplePageInfoFragmentViewModel::class.java]
    }
}