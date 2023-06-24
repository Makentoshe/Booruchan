package com.makentoshe.booruchan.screen.samples.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.screen.samples.SamplePageFragment

class SampleHorizontalViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val booru: Booru,
    private val tags: Set<Tag>
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int) = SamplePageFragment.create(position, booru, tags)

    override fun getCount() = Int.MAX_VALUE

    override fun saveState() = null
}