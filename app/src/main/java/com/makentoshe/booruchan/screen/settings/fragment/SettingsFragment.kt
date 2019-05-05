package com.makentoshe.booruchan.screen.settings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.screen.settings.controller.SettingsTabController
import com.makentoshe.booruchan.screen.settings.controller.SettingsViewPagerController
import com.makentoshe.booruchan.screen.settings.view.SettingsUi
import org.jetbrains.anko.AnkoContext
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope

class SettingsFragment : Fragment() {

    // Controller for view pager
    private val viewPagerController: SettingsViewPagerController by inject()

    // Controller for viewpager's tab layout
    private val tabController: SettingsTabController by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerController.bindView(view, childFragmentManager)
        tabController.bindView(view)
    }
}
