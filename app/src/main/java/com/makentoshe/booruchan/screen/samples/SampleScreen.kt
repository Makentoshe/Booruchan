package com.makentoshe.booruchan.screen.samples

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.navigation.Screen

class SampleScreen(
    private val position: Int,
    private val booru: Booru,
    private val tags: Set<Tag>
) : Screen() {
    override val fragment: Fragment
        get() = SampleContentFragment.create(position, booru, tags)
}