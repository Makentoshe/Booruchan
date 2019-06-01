package com.makentoshe.booruchan.screen.start

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.BooruFactoryImpl
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.network.fuel.FuelClientFactory
import com.makentoshe.booruchan.screen.SettingsScreen
import com.makentoshe.booruchan.screen.booru.BooruScreen
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.booru.gelbooru.Gelbooru
import com.makentoshe.boorulibrary.booru.safebooru.Safebooru
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
        val factory = BooruFactoryImpl(FuelClientFactory().buildClient())
        val b = when (booru) {
            is Gelbooru -> {
                factory.buildBooru(com.makentoshe.booruchan.api.gelbooru.Gelbooru::class.java)
            }
            is Safebooru-> {
                factory.buildBooru(com.makentoshe.booruchan.api.safebooru.Safebooru::class.java)
            }
            else -> return
        }
        router.navigateWithReplace(BooruScreen(b, setOf()))
    }
}