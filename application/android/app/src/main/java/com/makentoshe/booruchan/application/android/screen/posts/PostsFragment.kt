package com.makentoshe.booruchan.application.android.screen.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class PostsFragment : Fragment() {

    companion object {
        fun build(): PostsFragment {
            return PostsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(requireContext()).apply { text = "Posts screen not implemented" }
    }
}