package com.makentoshe.booruchan.postsamples.model

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.postsamples.PostSamplesContentScreen
import com.makentoshe.booruchan.postsamples.StartDownloadRxController

class SamplesVerticalViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val startDownloadRxController: StartDownloadRxController,
    private val booru: Booru
) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> Fragment()
            1 -> PostSamplesContentScreen(startDownloadRxController, booru).fragment
            else -> throw IllegalArgumentException(position.toString())
        }
    }

    override fun getCount() = 2

    override fun saveState(): Parcelable? {
        return null
    }

}