package com.makentoshe.booruchan.posts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.ViewModelFactory
import com.makentoshe.booruchan.booru.DrawerController
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import org.jetbrains.anko.AnkoContext

class PostsFragment : Fragment() {

    private lateinit var postsFragmentViewModel: PostsFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postsFragmentViewModel = buildViewModel(arguments!!)
        postsFragmentViewModel.update()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsFragmentUI(postsFragmentViewModel).createView(AnkoContext.create(requireContext(), this))
    }

    private fun buildViewModel(arguments: Bundle): PostsFragmentViewModel {
        val booru = arguments.getSerializable(Booru::class.java.simpleName) as Booru
        val drawerController = arguments.getSerializable(DrawerController::class.java.simpleName) as DrawerController
        val tags = arguments.getSerializable(Set::class.java.simpleName + Tag::class.java.simpleName) as Set<Tag>

        val factory = ViewModelFactory(booru = booru, drawerController = drawerController, tags = tags)
        return ViewModelProviders.of(this, factory)[PostsFragmentViewModel::class.java]
    }
}