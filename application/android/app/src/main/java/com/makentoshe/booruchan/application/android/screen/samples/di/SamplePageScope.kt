package com.makentoshe.booruchan.application.android.screen.samples.di

import com.makentoshe.booruchan.application.android.FullContentDownloadExecutor
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.samples.SamplePageFragment
import com.makentoshe.booruchan.application.android.screen.samples.navigation.SampleNavigation
import com.makentoshe.booruchan.core.context.BooruContext
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
    private val booruContexts by inject<List<BooruContext>>()
    private val fullContentDownloadExecutorBuilder by inject<FullContentDownloadExecutor.Builder>()

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)
        bind<SampleNavigation>().toInstance(SampleNavigation(router))

        val booruContext = booruContexts.first { it.javaClass == fragment.arguments.booruclass }
        val downloadExecutor = fullContentDownloadExecutorBuilder.build(booruContext, fragment)
        bind<FullContentDownloadExecutor>().toInstance(downloadExecutor)
    }
}