package com.makentoshe.booruchan.screen.settings.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.booruchan.screen.settings.fragment.SettingsPageFragment
import com.makentoshe.booruchan.screen.settings.model.SettingsScreenBuilder
import org.koin.core.KoinComponent
import org.koin.core.inject

class SettingsViewPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager), KoinComponent {

    private val screenBuilder by inject<SettingsScreenBuilder>()

    override fun getItem(position: Int) = SettingsPageFragment.create(position)

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return screenBuilder.build(position).screenKey
    }
}