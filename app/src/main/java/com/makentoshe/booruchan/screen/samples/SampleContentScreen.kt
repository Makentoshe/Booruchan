package com.makentoshe.booruchan.screen.samples

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen

class SampleContentScreen : Screen() {
    override val fragment: Fragment
        get() = SampleContentFragment()
}