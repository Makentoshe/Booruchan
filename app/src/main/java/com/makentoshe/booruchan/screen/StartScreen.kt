package com.makentoshe.booruchan.screen

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.booruview.BooruFragmentNavigator
import com.makentoshe.startview.StartFragment
import com.makentoshe.startview.StartFragmentNavigator

/**
 * Class describes a start screen.
 *
 * @param startFragmentNavigator is a navigator interface performs a navigation to another screens.
 */
class StartScreen(private val startFragmentNavigator: StartFragmentNavigator) : Screen() {

    /** Factory property creates a new [StartFragment] instance each call */
    override val fragment: Fragment
        get() = StartFragment.build(startFragmentNavigator)
}

/**
 * Class performs a navigation to another screens.
 */
class StartFragmentNavigator(
    private val router: Router, private val booruFragmentNavigator: BooruFragmentNavigator
) : StartFragmentNavigator {

    override fun navigateToSettingsScreen() {
        router.navigateWithReplace(SettingsScreen())
    }

    override fun navigateToBooruScreen(booru: Booru) {
        val booruScreen = BooruScreen(booru, setOf(), booruFragmentNavigator)
        router.navigateWithReplace(booruScreen)
    }
}