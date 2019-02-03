package com.makentoshe.booruchan.postsamplespageinfo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.PostSamplePageInfoFragmentViewModelFactory
import com.makentoshe.booruchan.PostsRepository
import com.makentoshe.booruchan.ViewModelFragment
import org.jetbrains.anko.*


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