package com.makentoshe.booruchan.screen.settings.screen

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.settings.fragment.SettingsWebmFragment

class SettingsWebmScreen(private val position: Int) : Screen() {
    override val fragment: Fragment
        get() = SettingsWebmFragment.create(position)

    override fun getScreenKey(): String {
        return "WebM"
    }
}