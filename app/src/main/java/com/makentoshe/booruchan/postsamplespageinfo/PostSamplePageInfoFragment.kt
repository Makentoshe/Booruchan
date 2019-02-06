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

    override val argumentInitializer: String
        get() = PostSamplePageInfoFragment::class.java.simpleName.plus(arguments!!.getInt(Int::class.java.simpleName))

    override fun clearArguments(arguments: Bundle?): Bundle? {
        return Bundle().apply {
            putInt(Int::class.java.simpleName, arguments!!.getInt(Int::class.java.simpleName))
        }
    }

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