package com.makentoshe.booruchan.screen.settings.common

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.settings.common.SettingsDefaultFragment

class SettingsDefaultScreen(private val position: Int) : Screen() {
    override val fragment: Fragment
        get() = SettingsDefaultFragment.create(position)

    override fun getScreenKey(): String {
        return "Default"
    }
}