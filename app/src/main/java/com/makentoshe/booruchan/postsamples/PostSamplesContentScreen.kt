package com.makentoshe.booruchan.postsamples

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.UnitRxController

class PostSamplesContentScreen(
    private val startDownloadRxController: UnitRxController,
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val position: Int
) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostSamplesContentFragment.create(startDownloadRxController, booru, tags, position)
}