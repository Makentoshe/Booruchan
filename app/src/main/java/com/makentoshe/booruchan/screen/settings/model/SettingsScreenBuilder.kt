package com.makentoshe.booruchan.screen.settings.model

import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.settings.screen.SettingsDefaultScreen
import com.makentoshe.booruchan.screen.settings.screen.SettingsWebmScreen
import java.io.Serializable

class SettingsScreenBuilder : Serializable {
    fun build(position: Int): Screen {
        return when (position) {
            0 -> SettingsDefaultScreen(position)
            1 -> SettingsWebmScreen(position)
            else -> throw IllegalArgumentException()
        }
    }
}