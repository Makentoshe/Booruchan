package com.makentoshe.booruimageview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ImageViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return ImageViewPagerElementFragment()
    }

    override fun getCount() = Int.MAX_VALUE
}
