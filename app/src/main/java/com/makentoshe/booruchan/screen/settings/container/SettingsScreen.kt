package com.makentoshe.booruchan.screen.settings.container

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.settings.container.SettingsFragment

class SettingsScreen : Screen() {
    override val fragment: Fragment
        get() = SettingsFragment()
}