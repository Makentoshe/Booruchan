package com.makentoshe.booruchan.postpreviews.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Backpressable
import com.makentoshe.booruchan.PostFragmentViewModelFactory
import com.makentoshe.booruchan.ViewModelFragment
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.postpreviews.PostsFragmentViewModel
import com.makentoshe.booruchan.postpreviews.model.OverflowController
import org.jetbrains.anko.AnkoContext

class PostsFragment : ViewModelFragment<PostsFragmentViewModel>() {

    override val argumentInitializer: String
        get() = PostsFragment::class.java.simpleName

    override fun buildViewModel(arguments: Bundle): PostsFragmentViewModel {
        val booru = arguments.getSerializable(Booru::class.java.simpleName) as Booru
        val drawerController = arguments.getSerializable(DrawerController::class.java.simpleName) as DrawerController
        val tags = arguments.getSerializable(Set::class.java.simpleName + Tag::class.java.simpleName) as Set<Tag>

        val factory = PostFragmentViewModelFactory(booru, drawerController, tags)
        return ViewModelProviders.of(this, factory)[PostsFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return PostsFragmentUI(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

    override fun onBackPressed(): Boolean {
        if (viewModel.overflowState == OverflowController.OverflowState.Cross) {
            viewModel.clickOverflowIcon()
            return true
        }

        //check inner fragment on backpress
        val fragment = childFragmentManager.fragments.lastOrNull()
        return fragment is Backpressable && fragment.onBackPressed()
    }
}