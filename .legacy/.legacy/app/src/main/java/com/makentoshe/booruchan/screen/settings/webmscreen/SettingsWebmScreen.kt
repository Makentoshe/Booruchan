package com.makentoshe.booruchan.screen.settings.webmscreen

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen

class SettingsWebmScreen(private val position: Int) : Screen() {
    override val fragment: Fragment
        get() = SettingsWebmFragment.create(position)

    override fun getScreenKey(): String {
        return "WebM"
    }
}