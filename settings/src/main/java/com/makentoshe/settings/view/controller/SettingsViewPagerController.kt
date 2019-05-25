package com.makentoshe.settings.view.controller

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.makentoshe.settings.view.SettingsViewPagerAdapter
import org.jetbrains.anko.find

class SettingsViewPagerController(private val fragmentManager: FragmentManager) {

    fun bindView(view: View) {
        val viewpager = view.find<ViewPager>(com.makentoshe.settings.R.id.settings_viewpager)
        viewpager.adapter = SettingsViewPagerAdapter(view.context, fragmentManager)
    }

    class Factory {
        fun build(fragmentManager: FragmentManager): SettingsViewPagerController {
            return SettingsViewPagerController(fragmentManager)
        }
    }
}
