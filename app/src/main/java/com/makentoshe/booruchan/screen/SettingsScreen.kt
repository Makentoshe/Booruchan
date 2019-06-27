package com.makentoshe.booruchan.screen

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.settings.common.SettingsBuilder
import com.makentoshe.settingsview.fragment.SettingsFragment

class SettingsScreen(private val settingsBuilder: SettingsBuilder) : Screen() {
    override val fragment: Fragment
        get() = SettingsFragment.build(settingsBuilder)
}