package com.makentoshe.booruchan.screen.start.model

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.booru.BooruScreen
import com.makentoshe.booruchan.screen.settings.screen.SettingsScreen

class StartScreenNavigator(private val router: Router) {
    fun navigateToSettingsScreen() {
        router.navigateWithReplace(SettingsScreen())
    }

    fun navigateToBooruScreen(booru: Booru) {
        router.navigateTo(BooruScreen(booru, setOf()))
    }
}