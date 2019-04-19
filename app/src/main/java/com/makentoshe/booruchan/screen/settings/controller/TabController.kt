package com.makentoshe.booruchan.screen.settings.controller

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.makentoshe.booruchan.R
import org.jetbrains.anko.find

class TabController {
    fun bindView(view: View) {
        val viewpager = view.find<ViewPager>(R.id.settings_viewpager)
        val viewpagertab = view.find<TabLayout>(R.id.settings_tab)
        viewpagertab.setupWithViewPager(viewpager)
    }
}