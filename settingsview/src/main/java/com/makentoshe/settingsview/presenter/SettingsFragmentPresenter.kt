package com.makentoshe.settingsview.presenter

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.makentoshe.settingsview.SettingsViewPagerAdapter
import com.makentoshe.settingsview.viewmodel.SettingsFragmentViewModel

/**
 * Presenter component used to binding viewmodel and view.
 */
class SettingsFragmentPresenter(
    private val fragmentManager: FragmentManager,
    private val viewmodel: SettingsFragmentViewModel
) {

    /** Bind [ViewPager] and [TabLayout] with [viewmodel] component */
    fun bindViewPager(view: ViewPager, tab: TabLayout?) {
        view.adapter = SettingsViewPagerAdapter(viewmodel.settingsBuilder, view.context, fragmentManager)
        tab?.setupWithViewPager(view)
    }
}
