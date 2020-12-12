package com.makentoshe.booruchan.application.android.screen.samples.di

import com.makentoshe.booruchan.application.android.FullContentDownloadExecutor
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.samples.SampleInfoFragment
import com.makentoshe.booruchan.core.context.BooruContext
import io.ktor.client.*
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.bind
import toothpick.ktp.delegate.inject
import javax.inject.Qualifier

@Qualifier
annotation class SampleInfoScope

class SampleInfoModule(fragment: SampleInfoFragment): Module() {

    private val fullContentDownloadExecutorBuilder by inject<FullContentDownloadExecutor.Builder>()
    private val booruContexts by inject<List<BooruContext>>()
    private val client by inject<HttpClient>()

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)

        val booruContext = booruContexts.first { it.javaClass == fragment.arguments.booruclass }
        val downloadExecutor = fullContentDownloadExecutorBuilder.build(booruContext, null)
        bind<FullContentDownloadExecutor>().toInstance(downloadExecutor)
    }
}
