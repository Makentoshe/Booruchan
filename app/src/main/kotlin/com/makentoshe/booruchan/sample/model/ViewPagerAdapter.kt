package com.makentoshe.booruchan.sample.model

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.sample.view.FragmentPage

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return FragmentPage.new(position)
    }

    override fun getCount() = Int.MAX_VALUE


}