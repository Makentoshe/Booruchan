package com.makentoshe.booruchan.screen.settings.controller

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.settings.model.SettingsViewPagerAdapter
import org.jetbrains.anko.find

class SettingsViewPagerController(private val fragmentManager: FragmentManager) {

    fun bindView(view: View) {
        val viewpager = view.find<ViewPager>(R.id.settings_viewpager)
        viewpager.adapter = SettingsViewPagerAdapter(fragmentManager)
    }

}