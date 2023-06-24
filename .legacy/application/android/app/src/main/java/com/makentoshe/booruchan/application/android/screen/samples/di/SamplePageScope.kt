package com.makentoshe.booruchan.application.android.screen.samples.di

import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.samples.SamplePageFragment
import com.makentoshe.booruchan.application.android.screen.samples.navigation.SampleNavigation
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.bind
import toothpick.ktp.delegate.inject
import javax.inject.Qualifier

@Qualifier
annotation class SamplePageScope

class SamplePageModule(fragment: SamplePageFragment) : Module() {

    private val router by inject<Router>()

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)
        bind<SampleNavigation>().toInstance(SampleNavigation(router))
    }
}