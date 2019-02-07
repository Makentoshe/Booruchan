package com.makentoshe.booruchan.start

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.FragmentScreen

class StartScreen : FragmentScreen() {
    override val fragment: Fragment
        get() = StartFragment()
}