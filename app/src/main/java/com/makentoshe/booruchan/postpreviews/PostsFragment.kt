package com.makentoshe.booruchan.postpreviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.booru.DrawerViewModel
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.postpreviews.view.PostsFragmentUI
import org.jetbrains.anko.AnkoContext

class PostsFragment : androidx.fragment.app.Fragment() {

    private lateinit var viewModel: PostsFragmentViewModel
    private lateinit var clearIconViewModel: ClearIconViewModel
    private lateinit var overflowViewModel: OverflowViewModel
    private lateinit var tagsViewModel: TagsViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var drawerController: DrawerController

    override fun onCreate(savedInstanceState: Bundle?) {
        val arguments = Companion.arguments
        drawerController = arguments.drawerController

        var factory: ViewModelProvider.NewInstanceFactory =
            PostsFragmentViewModel.Factory(arguments.booru, arguments.tags, arguments.drawerController)
        viewModel = ViewModelProviders.of(this, factory)[PostsFragmentViewModel::class.java]

        factory = ClearIconViewModel.Factory()
        clearIconViewModel = ViewModelProviders.of(this, factory)[ClearIconViewModel::class.java]

        factory = OverflowViewModel.Factory()
        overflowViewModel = ViewModelProviders.of(this, factory)[OverflowViewModel::class.java]

        factory = TagsViewModel.Factory(arguments.tags)
        tagsViewModel = ViewModelProviders.of(this, factory)[TagsViewModel::class.java]

        factory = SearchViewModel.Factory()
        searchViewModel = ViewModelProviders.of(this, factory)[SearchViewModel::class.java]

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.onCreateView(this)
        clearIconViewModel.onCreateView(this)
        overflowViewModel.onCreateView(this)
        tagsViewModel.onCreateView(this)
        searchViewModel.onCreateView(this)

        return PostsFragmentUI(
            viewModel,
            clearIconViewModel,
            overflowViewModel,
            tagsViewModel,
            searchViewModel,
            drawerController
        ).createView(AnkoContext.create(requireContext(), this))
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