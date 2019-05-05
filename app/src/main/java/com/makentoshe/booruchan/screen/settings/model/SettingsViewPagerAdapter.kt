package com.makentoshe.booruchan.screen.settings.model

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makentoshe.booruchan.screen.settings.fragment.SettingsPageFragment
import org.koin.core.KoinComponent
import org.koin.core.inject

class SettingsViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm), KoinComponent {

    private val screenBuilder by inject<SettingsScreenBuilder>()

    override fun getItem(position: Int) = SettingsPageFragment.create(position)

    override fun getCount() = 1

    override fun getPageTitle(position: Int) = screenBuilder.build(position).screenKey!!
}