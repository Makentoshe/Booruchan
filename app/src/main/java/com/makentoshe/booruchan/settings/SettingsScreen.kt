package com.makentoshe.booruchan.settings

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.FragmentScreen

class SettingsScreen : FragmentScreen() {
    override val fragment: Fragment
        get() = SettingsFragment()
}