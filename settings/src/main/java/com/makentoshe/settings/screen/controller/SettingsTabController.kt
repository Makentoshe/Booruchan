package com.makentoshe.settings.screen.controller

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class SettingsTabController {

    fun bindView(view: View) {
        val viewpager = view.findViewById<ViewPager>(com.makentoshe.settings.R.id.settings_viewpager)
        val tablayout = view.findViewById<TabLayout>(com.makentoshe.settings.R.id.settings_tab)
        tablayout.setupWithViewPager(viewpager)
    }

    class Factory {
        fun build() = SettingsTabController()
    }
}
