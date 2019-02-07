package com.makentoshe.booruchan.postsample

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.FragmentScreen

class PostSampleScreen(private val position: Int): FragmentScreen() {
    override val fragment: Fragment
        get() = PostSampleFragment().apply {
            //put important arguments
            arguments = Bundle().apply {
                putInt(Int::class.java.simpleName, position)
            }
        }
}