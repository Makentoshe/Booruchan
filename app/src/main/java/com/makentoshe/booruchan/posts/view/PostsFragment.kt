package com.makentoshe.booruchan.posts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.Action
import com.makentoshe.booruchan.BackPressableFragment
import com.makentoshe.booruchan.ViewModelFactory
import com.makentoshe.booruchan.booru.DrawerController
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import com.makentoshe.booruchan.posts.model.OverflowState
import org.jetbrains.anko.AnkoContext

class PostsFragment : Fragment(), BackPressableFragment {

    private lateinit var postsFragmentViewModel: PostsFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val booru = arguments?.getSerializable(Booru::class.java.simpleName) as Booru
        val drawerController = arguments?.getSerializable(DrawerController::class.java.simpleName) as DrawerController
        val factory = ViewModelFactory(booru = booru, drawerController = drawerController)
        postsFragmentViewModel = ViewModelProviders.of(this, factory)[PostsFragmentViewModel::class.java]
        postsFragmentViewModel.update()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsFragmentUI(postsFragmentViewModel).createView(AnkoContext.create(requireContext(), this))
    }

    override fun onBackPressed(): Boolean {
        val list = childFragmentManager.fragments
        for (i in list.lastIndex downTo 0) {
            val fragment = list[i]
            if (fragment is BackPressableFragment && fragment.onBackPressed()) return true
        }

        val overflowController = postsFragmentViewModel.uiController.overflowController
        if (overflowController.value == OverflowState.Cross) {
            overflowController.newState(OverflowState.Transition(OverflowState.Magnify))
            return true

        }

        return false
    }
}