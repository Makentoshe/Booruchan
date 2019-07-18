package com.makentoshe.booruchan.screen

import androidx.fragment.app.Fragment
import com.makentoshe.api.repository.BooruRepository
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.booruview.BooruFragmentNavigator
import com.makentoshe.booruview.BooruTransitionData
import com.makentoshe.settings.common.SettingsBuilder
import com.makentoshe.startview.StartFragment
import com.makentoshe.startview.StartFragmentNavigator

/**
 * Class describes a start screen.
 *
 * @param startFragmentNavigator is a navigator interface performs a navigation to another screens.
 */
class StartScreen(
    private val settingsBuilder: SettingsBuilder,
    private val startFragmentNavigator: StartFragmentNavigator,
    private val booruRepository: BooruRepository
) : Screen() {

    /** Factory property creates a new [StartFragment] instance each call */
    override val fragment: Fragment
        get() = StartFragment.build(settingsBuilder, startFragmentNavigator, booruRepository)
}

/**
 * Class performs a navigation to another screens.
 */
class StartFragmentNavigator(
    private val router: Router,
    private val booruFragmentNavigator: BooruFragmentNavigator,
    private val settingsBuilder: SettingsBuilder
) : StartFragmentNavigator {

    override fun navigateToSettingsScreen() {
        router.navigateWithReplace(SettingsScreen(settingsBuilder))
    }

    override fun navigateToBooruScreen(booru: Booru) {
        val tags = setOf(booru.tagFactory.build("webm"))
        val boorudata = BooruTransitionData(booru, tags)
        val booruScreen = BooruScreen(boorudata, booruFragmentNavigator)
        router.navigateWithReplace(booruScreen)
    }
}