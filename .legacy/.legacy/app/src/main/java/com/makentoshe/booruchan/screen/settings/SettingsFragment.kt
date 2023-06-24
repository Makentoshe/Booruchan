package com.makentoshe.booruchan.screen.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.settings.model.SettingsScreenBuilder
import com.makentoshe.booruchan.screen.settings.model.SettingsViewPagerAdapter
import com.makentoshe.booruchan.screen.settings.view.SettingsUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val screenBuilder = SettingsScreenBuilder()
        val adapter =
            SettingsViewPagerAdapter(childFragmentManager, screenBuilder)
        val viewpager = view.find<ViewPager>(R.id.settings_viewpager)
        viewpager.adapter = adapter
        val viewpagertab = view.find<TabLayout>(R.id.settings_tab)
        viewpagertab.setupWithViewPager(viewpager)
    }
}