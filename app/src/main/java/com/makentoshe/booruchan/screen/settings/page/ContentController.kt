package com.makentoshe.booruchan.screen.settings.page

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R

class ContentController(private val screenBuilder: SettingsScreenBuilder) {

    fun bindView(position: Int, fragmentManager: FragmentManager) {
        val fragment = screenBuilder.build(position).fragment
        setFragment(fragmentManager, fragment)
    }

    private fun setFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        fragmentManager.beginTransaction().add(R.id.settings_container, fragment).commit()
    }
}