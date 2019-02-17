package com.makentoshe.booruchan.postpreview

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentScreen

class PostPageScreen(
    private val position: Int,
    private val booru: Booru,
    private val tags: Set<Tag>
) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostPageFragment.create(position, booru, tags)
}