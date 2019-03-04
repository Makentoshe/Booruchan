package com.makentoshe.booruchan.postsamples

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentScreen

class PostSamplesContentScreen(
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val position: Int,
    private val fullScreenController: FullScreenController
) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostSamplesContentFragment.create(booru, tags, position, fullScreenController)
}