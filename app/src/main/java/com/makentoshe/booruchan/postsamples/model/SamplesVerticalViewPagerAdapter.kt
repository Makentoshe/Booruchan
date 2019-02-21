package com.makentoshe.booruchan.postsamples.model

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.UnitRxController
import com.makentoshe.booruchan.postsamples.PostSamplesContentScreen

class SamplesVerticalViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val startDownloadRxController: UnitRxController,
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val position: Int
) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Fragment()
            1 -> PostSamplesContentScreen(startDownloadRxController, booru, tags, this.position).fragment
            else -> throw IllegalArgumentException(position.toString())
        }
    }

    override fun getCount() = 2

    override fun saveState(): Parcelable? {
        return null
    }

}