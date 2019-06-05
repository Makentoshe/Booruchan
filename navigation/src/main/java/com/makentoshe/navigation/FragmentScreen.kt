package com.makentoshe.navigation

import androidx.fragment.app.Fragment

/**
 * Class for boxing fragment into [Screen] instance
 */
class FragmentScreen(private val targetFragment: Fragment) : Screen() {
    override val fragment: Fragment
        get() = targetFragment
}