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
import java.io.Serializable

class BooruFragment : androidx.fragment.app.Fragment() {

    private lateinit var drawerViewModel: DrawerViewModel
    private lateinit var contentScreenViewModel: ContentScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val booru = arguments!![BOORU] as Booru
        val tags = arguments!![TAGS] as Set<Tag>

        //create drawer controller
        var factory: ViewModelProvider.NewInstanceFactory = DrawerViewModel.Factory()
        drawerViewModel = ViewModelProviders.of(this, factory)[DrawerViewModel::class.java]
        //create content screens controller
        factory = ContentScreenViewModel.Factory(booru, drawerViewModel, tags)
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
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun create(booru: Booru, tags: Set<Tag>): androidx.fragment.app.Fragment {
            return BooruFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(BOORU, booru)
                    putSerializable(TAGS, tags as Serializable)
                }
            }
        }
    }
}