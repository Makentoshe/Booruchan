package com.makentoshe.settingsview

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.settings.common.SettingsBuilder
import com.makentoshe.settingsview.fragment.DefaultSettingsFragment

/** [androidx.viewpager.widget.ViewPager] adapter performs a settings screens displaying */
class SettingsViewPagerAdapter(
    private val settingsBuilder: SettingsBuilder,
    private val context: Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = when (position) {
        0 -> DefaultSettingsFragment.build(settingsBuilder)
        else -> throw IllegalArgumentException(position.toString())
    }

    override fun getCount() = 1

    override fun getPageTitle(position: Int) = when (position) {
        0 -> DefaultSettingsFragment.title(context)
        else -> throw IllegalArgumentException(position.toString())
    }
}
