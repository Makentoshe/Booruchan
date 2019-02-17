package com.makentoshe.booruchan.postsample

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.FragmentScreen

class PostSampleScreen(private val position: Int, private val booru: Booru) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostSampleFragment.create(position, booru)
}