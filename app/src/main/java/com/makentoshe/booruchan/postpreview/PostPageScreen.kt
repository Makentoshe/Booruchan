package com.makentoshe.booruchan.postpreview

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.FragmentScreen

class PostPageScreen(
    private val arguments: PostPageFragment.Arguments,
    private val position: Int
) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostPageFragment.create(position, arguments)
}