package com.makentoshe.booruchan.screen.start

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.BooruFactory
import com.makentoshe.booruchan.api.BooruFactoryImpl
import com.makentoshe.booruchan.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.api.rule34.Rule34
import com.makentoshe.booruchan.api.safebooru.Safebooru
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.fuel.FuelClientFactory
import com.makentoshe.booruchan.screen.start.controller.ContentController
import com.makentoshe.booruchan.screen.start.controller.OverflowController
import com.makentoshe.booruchan.screen.start.model.BooruContentFilter
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val startModule = module {
    scope(named<StartFragment>()) {
        scoped<List<Class<out Booru>>> {
            ArrayList<Class<out Booru>>().apply {
                add(Gelbooru::class.java)
                add(Safebooru::class.java)
                add(Rule34::class.java)
            }
        }
        scoped<HttpClient> { FuelClientFactory().buildClient() }
        scoped<BooruFactory> { BooruFactoryImpl(get()) }
        scoped { StartScreenNavigator(get()) }
        scoped { OverflowController(get()) }
        scoped { BooruContentFilter(get(), androidContext(), get()) }
        scoped { ContentController(get(), get(), get()) }
    }
}
