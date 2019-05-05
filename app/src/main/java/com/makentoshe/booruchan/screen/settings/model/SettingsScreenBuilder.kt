package com.makentoshe.booruchan.screen.settings.model

import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.settings.SettingsDefaultScreen
import java.io.Serializable

class SettingsScreenBuilder : Serializable {

    fun build(position: Int): Screen {
        return when (position) {
            0 -> SettingsDefaultScreen(position)
            else -> throw IllegalArgumentException()
        }
    }
}