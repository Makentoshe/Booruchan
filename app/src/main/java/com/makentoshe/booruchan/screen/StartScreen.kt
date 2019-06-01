package com.makentoshe.booruchan.screen

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.booru.BooruScreen
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.startview.StartFragment
import com.makentoshe.startview.StartFragmentNavigator

class StartScreen(router: Router) : Screen() {

    private val navigator = StartFragmentNavigator(router)

    override val fragment: Fragment
        get() = StartFragment.build(navigator)
}

class StartFragmentNavigator(private val router: Router) : StartFragmentNavigator {

    override fun navigateToSettingsScreen() {
        router.navigateWithReplace(SettingsScreen())
    }

    override fun navigateToBooruScreen(booru: Booru) {
        router.navigateWithReplace(BooruScreen(booru, setOf()))
    }
}