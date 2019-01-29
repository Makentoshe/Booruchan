package com.makentoshe.booruchan.posts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Backpressable
import com.makentoshe.booruchan.ViewModelFactory
import com.makentoshe.booruchan.ViewModelFragment
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import com.makentoshe.booruchan.posts.model.OverflowState
import org.jetbrains.anko.AnkoContext

class PostsFragment : ViewModelFragment<PostsFragmentViewModel>() {

    override fun buildViewModel(arguments: Bundle?): PostsFragmentViewModel {
        val booru = arguments!!.getSerializable(Booru::class.java.simpleName) as Booru
        val drawerController = arguments.getSerializable(DrawerController::class.java.simpleName) as DrawerController
        val tags = arguments.getSerializable(Set::class.java.simpleName + Tag::class.java.simpleName) as Set<Tag>

        val factory = ViewModelFactory(booru = booru, drawerController = drawerController, tags = tags)
        return ViewModelProviders.of(this, factory)[PostsFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return PostsFragmentUI(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

    override fun onBackPressed(): Boolean {
        val overflowController = viewModel.uiController.overflowController

        if (overflowController.value == OverflowState.Cross) {
            overflowController.newState(OverflowState.Transition(OverflowState.Magnify))
            return true
        }

        //check inner fragment on backpress
        val fragment = childFragmentManager.fragments.last()
        return fragment is Backpressable && fragment.onBackPressed()
    }
}