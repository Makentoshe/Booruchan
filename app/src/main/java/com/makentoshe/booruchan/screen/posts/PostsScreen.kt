package com.makentoshe.booruchan.screen.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen

class PostsScreen : Screen() {
    override val fragment: Fragment
        get() = PostsFragment()
}

class PostsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(requireContext()).apply {
            text = "Posts"
        }
    }
}