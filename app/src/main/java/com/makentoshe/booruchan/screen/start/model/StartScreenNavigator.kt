package com.makentoshe.booruchan.screen.start.model

import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.booru.BooruScreen
import com.makentoshe.booruchan.screen.settings.SettingsScreen

class StartScreenNavigator(private val router: Router) {
    fun navigateToSettingsScreen() {
        router.navigateTo(SettingsScreen())
    }

    fun navigateToBooruScreen(booru: Booru, tags: Set<Tag>) {
        router.navigateTo(BooruScreen(booru, tags))
    }
}