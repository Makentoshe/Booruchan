package com.makentoshe.booruchan.screen.settings

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.model.RequestCode
import com.makentoshe.booruchan.navigation.Screen

class SettingsScreen(private val target: Fragment) : Screen() {
    override val fragment: Fragment
        get() = SettingsFragment().apply {
            setTargetFragment(target, RequestCode.settings)
        }
}