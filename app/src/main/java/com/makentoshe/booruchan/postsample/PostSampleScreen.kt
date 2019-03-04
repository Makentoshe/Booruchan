package com.makentoshe.booruchan.postsample

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.postsamples.FullScreenController

class PostSampleScreen(
    private val position: Int,
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val fullScreenController: FullScreenController
) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostSampleFragment.create(position, booru, tags, fullScreenController)
}