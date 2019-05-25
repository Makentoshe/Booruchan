package com.makentoshe.settings.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.settings.screen.view.SettingsFragmentUi
import com.makentoshe.settings.screen.controller.SettingsTabController
import com.makentoshe.settings.screen.controller.SettingsViewPagerController
import org.jetbrains.anko.AnkoContext

class SettingsFragment : Fragment() {

    lateinit var tabControllerFactory: SettingsTabController.Factory
        private set

    lateinit var viewpagerControllerFactory: SettingsViewPagerController.Factory
        private set

    private val viewpagerController: SettingsViewPagerController by lazy {
        viewpagerControllerFactory.build(childFragmentManager)
    }

    private val tabController: SettingsTabController by lazy {
        tabControllerFactory.build()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsFragmentUi().createView(AnkoContext.create(requireContext(), false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabController.bindView(view)
        viewpagerController.bindView(view)
    }

    class Factory {
        fun build(): SettingsFragment {
            val fragment = SettingsFragment()
            fragment.tabControllerFactory = SettingsTabController.Factory()
            fragment.viewpagerControllerFactory = SettingsViewPagerController.Factory()
            return fragment
        }
    }
}
