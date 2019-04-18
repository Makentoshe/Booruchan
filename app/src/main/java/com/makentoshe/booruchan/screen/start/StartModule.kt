package com.makentoshe.booruchan.screen.start

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.BooruFactory
import com.makentoshe.booruchan.api.BooruFactoryImpl
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.fuel.FuelClientFactory
import com.makentoshe.booruchan.screen.start.model.BooruContentFilter
import com.makentoshe.booruchan.screen.start.controller.ContentController
import com.makentoshe.booruchan.screen.start.controller.OverflowController
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named

fun Module.buildStartScope(booruList: List<Class<out Booru>>) =
    scope(named<StartFragment>()) {
        scoped<HttpClient> { FuelClientFactory().buildClient() }
        scoped<BooruFactory> { BooruFactoryImpl(get()) }
        scoped { StartScreenNavigator(get()) }
        scoped { OverflowController(get()) }
        scoped { BooruContentFilter(get(), androidContext(), booruList) }
        scoped { ContentController(get(), get(), get()) }
    }
