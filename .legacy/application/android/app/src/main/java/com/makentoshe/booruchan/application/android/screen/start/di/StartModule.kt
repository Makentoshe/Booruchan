package com.makentoshe.booruchan.application.android.screen.start.di

import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.start.navigation.StartNavigation
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.bind
import toothpick.ktp.delegate.inject

class StartModule : Module() {

    private val router by inject<Router>()

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)

        val navigation = StartNavigation(router)
        bind<StartNavigation>().toInstance(navigation)
    }
}