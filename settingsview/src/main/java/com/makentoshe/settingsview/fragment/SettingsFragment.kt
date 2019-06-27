package com.makentoshe.settingsview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.makentoshe.settings.common.SettingsBuilder
import com.makentoshe.settingsview.presenter.SettingsFragmentPresenter
import com.makentoshe.settingsview.viewmodel.SettingsFragmentViewModel
import com.makentoshe.settingsview.view.SettingsFragmentUi
import org.jetbrains.anko.AnkoContext

/**
 * Main settings screen contains viewpager. Each settings type will be placed on its own page.
 */
class SettingsFragment : Fragment() {

    /** SHOULD NOT BEING USED. INSTANCE WILL BE null AFTER FRAGMENT RECREATION.
     * USE [SettingsBuilder] INSTANCE FROM [SettingsFragmentViewModel] component */
    private var settingsBuilder: SettingsBuilder? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SettingsFragmentUi().createView(AnkoContext.create(requireContext(), false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // build viewmodel
        val factory = SettingsFragmentViewModel.Factory(settingsBuilder)
        val viewmodel = ViewModelProviders.of(this, factory)[SettingsFragmentViewModel::class.java]
        // build presenter
        val presenter = SettingsFragmentPresenter(childFragmentManager, viewmodel)
        // bind viewpager
        val viewpager = view.findViewById<ViewPager>(com.makentoshe.settingsview.R.id.settings_viewpager)
        val tablayout = view.findViewById<TabLayout>(com.makentoshe.settingsview.R.id.settings_tab)
        presenter.bindViewPager(viewpager, tablayout)
    }

    companion object {
        /** Creates [SettingsFragment] instance used [SettingsBuilder] */
        fun build(settingsBuilder: SettingsBuilder): SettingsFragment {
            val fragment = SettingsFragment()
            fragment.settingsBuilder = settingsBuilder
            return fragment
        }
    }
}
