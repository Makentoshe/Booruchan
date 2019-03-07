package com.makentoshe.booruchan.screen.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.screen.arguments
import org.jetbrains.anko.*

class PostsFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        PostsUiToolbarInflator(booru).inflate(view)
    }

    companion object {
        private const val BOORU = "Booru"

        fun create(booru: Booru) = PostsFragment().apply {
            this.booru = booru
        }
    }
}


