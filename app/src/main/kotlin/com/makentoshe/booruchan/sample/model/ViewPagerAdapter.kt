package com.makentoshe.booruchan.sample.model

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.makentoshe.booruchan.sample.SampleViewModel
import com.makentoshe.booruchan.sample.view.PageFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, private val viewModel: SampleViewModel)
    : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = PageFragment.new(position, viewModel.tagsString)

    override fun getCount() = Int.MAX_VALUE

}