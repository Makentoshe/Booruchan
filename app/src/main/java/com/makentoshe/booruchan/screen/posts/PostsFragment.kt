package com.makentoshe.booruchan.screen.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.BooruToolbarUiInflater
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.posts.inflator.PostsUiContentInflator
import com.makentoshe.booruchan.screen.posts.inflator.PostsUiToolbarInflator
import com.makentoshe.booruchan.screen.posts.inflator.PostsUiToolbarSearchInflator
import com.makentoshe.booruchan.screen.posts.model.*
import com.makentoshe.booruchan.screen.posts.view.PostsUi
import org.jetbrains.anko.*

class PostsFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private val tagsController by lazy {
        TagsHolderViewModel.create(this, TagsControllerImpl())
    }

    private val searchController by lazy {
        SearchViewModel.create(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        PostsUiToolbarInflator(booru).inflate(view)
        PostsUiToolbarSearchInflator(childFragmentManager, tagsController, searchController)
            .inflate(view)

        PostsUiContentInflator(searchController, childFragmentManager, booru)
            .inflate(view)

        parentFragment?.view?.findViewById<DrawerLayout>(R.id.booru_drawer)?.let {
            BooruToolbarUiInflater(it).inflate(view)
        }
    }

    companion object {
        private const val BOORU = "Booru"

        fun create(booru: Booru) = PostsFragment().apply {
            this.booru = booru
        }
    }
}

