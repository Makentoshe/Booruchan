package com.makentoshe.booruchan.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.ViewModelFactory
import com.makentoshe.booruchan.booru.DrawerController
import org.jetbrains.anko.AnkoContext

class PostsFragment : Fragment() {

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
}