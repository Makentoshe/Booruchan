package com.makentoshe.booruchan.postsample

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.FragmentScreen

class PostSampleScreen: FragmentScreen() {
    override val fragment: Fragment
        get() = PostSampleFragment()
}