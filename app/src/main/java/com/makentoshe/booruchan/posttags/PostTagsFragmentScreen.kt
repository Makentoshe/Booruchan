package com.makentoshe.booruchan.posttags

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentScreen

class PostTagsFragmentScreen(
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val position: Int
) : FragmentScreen() {

    override val fragment: Fragment
    get() = Fragment()
//        get() = PostTagsFragment.create(booru, tags, position)
}