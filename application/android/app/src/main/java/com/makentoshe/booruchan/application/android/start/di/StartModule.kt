package com.makentoshe.booruchan.application.android.start.di

import JsonDanbooruContext
import XmlGelbooruContext
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.start.navigation.StartNavigation
import context.BooruContext
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.bind
import toothpick.ktp.delegate.inject

class StartModule: Module() {

    private val router by inject<Router>()

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)

        val booruContexts = listOf<BooruContext>(XmlGelbooruContext(), JsonDanbooruContext())
        bind<List<BooruContext>>().toInstance(booruContexts)

        val navigation = StartNavigation(router)
        bind<StartNavigation>().toInstance(navigation)
    }
}