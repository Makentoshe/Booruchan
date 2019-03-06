package com.makentoshe.booruchan.screen.start.model

import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.settings.SettingsScreen

class StartScreenNavigator(private val router: Router) {
    fun navigateToSettingsScreen() {
        router.navigateTo(SettingsScreen())
    }

    fun navigateToBooruScreen() {

    }
}