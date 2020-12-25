package com.makentoshe.booruchan.screen.start

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen

class StartScreen: Screen() {
    override val fragment: Fragment
        get() = StartFragment()
}