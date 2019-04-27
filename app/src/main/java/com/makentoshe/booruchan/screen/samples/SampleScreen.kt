package com.makentoshe.booruchan.screen.samples

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.samples.fragment.SampleSwipeFragment

class SampleScreen(
    private val position: Int,
    private val booru: Booru,
    private val tags: Set<Tag>
) : Screen() {
    override val fragment: Fragment
        get() = SampleSwipeFragment.create(position, booru, tags)
}