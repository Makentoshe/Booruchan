package com.makentoshe.booruchan.postsamples

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.FragmentScreen

class PostSamplesScreen(
    private val itemPosition: Int,
    private val position: Int
) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostSamplesFragment.create(itemPosition, position)

}