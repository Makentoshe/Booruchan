package com.makentoshe.booruchan.screen.settings.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.booruchan.screen.settings.SettingsPageFragment

class SettingsViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val screenBuilder: SettingsScreenBuilder
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int) =
        SettingsPageFragment.create(position, screenBuilder)

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return screenBuilder.build(position).screenKey
    }
}