package com.makentoshe.booruchan.screen.settings.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.jetbrains.anko.AnkoContext
import org.koin.androidx.scope.currentScope

class SettingsFragment : Fragment() {

    // Controller for view pager
    private val viewPagerController: ViewPagerController by currentScope.inject()

    // Controller for viewpager's tab layout
    private val tabController: TabController by currentScope.inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerController.bindView(view, childFragmentManager)
        tabController.bindView(view)
    }
}
