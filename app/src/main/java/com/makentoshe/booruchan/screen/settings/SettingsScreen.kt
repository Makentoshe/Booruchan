package com.makentoshe.booruchan.screen.settings

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen

class SettingsScreen : Screen() {
    override val fragment: Fragment
        get() = SettingsFragment()
}