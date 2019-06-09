package com.makentoshe.settings.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.settings.screen.controller.SettingsTabController
import com.makentoshe.settings.screen.controller.SettingsViewPagerController
import com.makentoshe.settings.screen.view.SettingsFragmentUi
import org.jetbrains.anko.AnkoContext

class SettingsFragment : Fragment() {

    private val viewpagerController: SettingsViewPagerController by lazy {
        SettingsViewPagerController(childFragmentManager)
    }

    private val tabController: SettingsTabController by lazy {
        SettingsTabController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsFragmentUi().createView(AnkoContext.create(requireContext(), false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabController.bindView(view)
        viewpagerController.bindView(view)
    }

    companion object {
        fun build(): SettingsFragment {
            return SettingsFragment()
        }
    }
}
