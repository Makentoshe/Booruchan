package com.makentoshe.booruchan.postinfo

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentScreen

class PostInfoScreen(
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val position: Int
): FragmentScreen() {
    override val fragment: Fragment
        get() = PostInfoFragment.create(booru, tags, position)
}
