package com.makentoshe.settings.view

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.settings.view.fragment.DefaultSettingsFragment

class SettingsViewPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = when (position) {
        0 -> DefaultSettingsFragment.Factory().build()
        else -> throw IllegalArgumentException(position.toString())
    }

    override fun getCount() = 1

    override fun getPageTitle(position: Int) = when (position) {
        0 -> DefaultSettingsFragment.Factory().title(context)
        else -> throw IllegalArgumentException(position.toString())
    }
}
