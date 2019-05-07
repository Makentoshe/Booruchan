package com.makentoshe.booruchan.screen.settings.controller

import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.settings.model.SettingsScreenBuilder

class SettingsPageController(
    private val position: Int,
    private val fragmentManager: FragmentManager,
    private val screenBuilder: SettingsScreenBuilder
) {

    fun bindView() {
        val fragment = screenBuilder.build(position).fragment
        val tag = fragment::class.java.simpleName.plus(position)
        fragmentManager.beginTransaction().add(R.id.settings_container, fragment, tag).commit()
    }
}