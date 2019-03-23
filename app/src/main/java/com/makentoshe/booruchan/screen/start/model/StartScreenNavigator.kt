package com.makentoshe.booruchan.screen.start.model

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.booru.BooruScreen
import com.makentoshe.booruchan.screen.settings.SettingsScreen

class StartScreenNavigator(private val router: Router) {
    fun navigateToSettingsScreen(target: Fragment) {
        router.navigateTo(SettingsScreen(target))
    }

    fun navigateToBooruScreen(booru: Booru) {
        router.navigateTo(BooruScreen(booru))
    }
}