package com.makentoshe.booruchan.screen.settings.defaultscreen

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen

class SettingsDefaultScreen(private val position: Int) : Screen() {
    override val fragment: Fragment
        get() = SettingsDefaultFragment.create(position)

    override fun getScreenKey(): String {
        return "Default"
    }
}