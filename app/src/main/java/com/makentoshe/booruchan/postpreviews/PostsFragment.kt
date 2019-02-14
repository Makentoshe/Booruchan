package com.makentoshe.booruchan.postpreviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.postpreviews.model.AdapterBuilder
import com.makentoshe.booruchan.postpreviews.model.AdapterBuilderImpl
import com.makentoshe.booruchan.postpreviews.view.PostsFragmentUi
import com.makentoshe.booruchan.postpreviews.viewmodel.*
import org.jetbrains.anko.AnkoContext

class PostsFragment : androidx.fragment.app.Fragment() {

    private lateinit var clearIconViewModel: ClearIconViewModel
    private lateinit var overflowViewModel: OverflowViewModel
    private lateinit var tagsViewModel: TagsViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var drawerController: DrawerController
    private lateinit var viewPagerViewModel: ViewPagerViewModel
    private lateinit var adapterBuilder: AdapterBuilder
    private lateinit var booru: Booru

    override fun onCreate(savedInstanceState: Bundle?) {
        val arguments = Companion.arguments
        booru = arguments.booru
        drawerController = arguments.drawerController
        adapterBuilder = AdapterBuilderImpl(arguments.booru)

        var factory: ViewModelProvider.NewInstanceFactory = ClearIconViewModel.Factory()
        clearIconViewModel = ViewModelProviders.of(this, factory)[ClearIconViewModel::class.java]

        factory = OverflowViewModel.Factory()
        overflowViewModel = ViewModelProviders.of(this, factory)[OverflowViewModel::class.java]

        factory = TagsViewModel.Factory(arguments.tags)
        tagsViewModel = ViewModelProviders.of(this, factory)[TagsViewModel::class.java]

        factory = SearchViewModel.Factory()
        searchViewModel = ViewModelProviders.of(this, factory)[SearchViewModel::class.java]

        factory = ViewPagerViewModel.Factory()
        viewPagerViewModel = ViewModelProviders.of(this, factory)[ViewPagerViewModel::class.java]

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tagsViewModel.onCreateView(this)
        searchViewModel.onCreateView(this)
        overflowViewModel.onCreateView(this)
        viewPagerViewModel.onCreateView(this)
        clearIconViewModel.onCreateView(this)

        return PostsFragmentUi(
            clearIconViewModel,
            overflowViewModel,
            tagsViewModel,
            searchViewModel,
            drawerController,
            viewPagerViewModel,
            adapterBuilder,
            booru.title
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