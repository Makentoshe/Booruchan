package com.makentoshe.booruchan.screen.samples.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.screen.samples.SampleFragment

class SampleVerticalViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val position: Int,
    private val booru: Booru,
    private val tags: Set<Tag>
) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Fragment()
            1 -> SampleFragment.create(this.position, booru, tags)
            else -> throw IllegalArgumentException(position.toString())
        }
    }

    override fun getCount() = 2

    override fun saveState() = null
}