package com.makentoshe.booruchan.screen.samples

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen

class SampleScreen : Screen() {
    override val fragment: Fragment
        get() = SampleFragment()
}