package com.makentoshe.booruchan.postpreviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.postpreviews.view.PostsFragmentUI
import org.jetbrains.anko.AnkoContext

class PostsFragment : androidx.fragment.app.Fragment() {

    private lateinit var viewModel: PostsFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val arguments = Companion.arguments
        val factory = PostsFragmentViewModel.Factory(arguments.booru, arguments.tags, arguments.drawerController)
        viewModel = ViewModelProviders.of(this, factory)[PostsFragmentViewModel::class.java]

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.onCreateView(this)
        return PostsFragmentUI(viewModel).createView(AnkoContext.create(requireContext(), this))
    }

    companion object {
        fun create(booru: Booru, drawerController: DrawerController, tags: Set<Tag>): androidx.fragment.app.Fragment {
            arguments = Arguments(booru, drawerController, tags)
            return PostsFragment()
        }

        private lateinit var arguments: Arguments

        private data class Arguments(val booru: Booru, val drawerController: DrawerController, val tags: Set<Tag>)
    }
}