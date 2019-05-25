package com.makentoshe.booruchan.screen

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.settings.screen.fragment.SettingsFragment

class SettingsScreen : Screen() {
    override val fragment: Fragment
        get() = SettingsFragment.Factory().build()
}