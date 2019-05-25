package com.makentoshe.booruchan.screen.start.model

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.booru.BooruScreen
import com.makentoshe.booruchan.screen.SettingsScreen
import org.koin.core.KoinComponent
import org.koin.core.inject

class StartScreenNavigator(private val initialTagSet: Set<Tag>): KoinComponent {

    private val router by inject<Router>()

    fun navigateToSettingsScreen() {
        router.navigateWithReplace(SettingsScreen())
    }

    fun navigateToBooruScreen(booru: Booru) {
        router.navigateTo(BooruScreen(booru, initialTagSet))
    }
}