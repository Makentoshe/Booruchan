package com.makentoshe.booruchan.booru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Fragment
import com.makentoshe.booruchan.booru.view.BooruFragmentUI
import org.jetbrains.anko.AnkoContext

class BooruFragment : androidx.fragment.app.Fragment() {

    private lateinit var drawerViewModel: DrawerViewModel
    private lateinit var contentScreenViewModel: ContentScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val arguments = Companion.arguments
        //create drawer controller
        var factory: ViewModelProvider.NewInstanceFactory = DrawerViewModel.Factory()
        drawerViewModel = ViewModelProviders.of(this, factory)[DrawerViewModel::class.java]
        //create content screens controller
        factory = ContentScreenViewModel.Factory(arguments.booru, drawerViewModel, arguments.tags)
        contentScreenViewModel = ViewModelProviders.of(this, factory)[ContentScreenViewModel::class.java]
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentScreenViewModel.onCreateView(this)
        drawerViewModel.onCreateView(this)

        return BooruFragmentUI(contentScreenViewModel, drawerViewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }

    companion object {

        fun create(booru: Booru, tags: HashSet<Tag>): androidx.fragment.app.Fragment {
            arguments = Arguments(booru, tags)
            return BooruFragment()
        }

        private lateinit var arguments: Arguments

        private data class Arguments(val booru: Booru, val tags: Set<Tag>)
    }
}