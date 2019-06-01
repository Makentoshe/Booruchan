package com.makentoshe.booruchan.screen.start

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.BooruFactory
import com.makentoshe.booruchan.api.BooruFactoryImpl
import com.makentoshe.booruchan.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.api.rule34.Rule34
import com.makentoshe.booruchan.api.safebooru.Safebooru
import com.makentoshe.booruchan.network.HttpClient
import com.makentoshe.booruchan.network.fuel.FuelClientFactory
import com.makentoshe.booruchan.screen.start.controller.StartContentController
import com.makentoshe.booruchan.screen.start.controller.StartOverflowController
import com.makentoshe.booruchan.screen.start.model.StartScreenNavigator

object StartModule {

    val module = org.koin.dsl.module {
        /* Http client for BooruFactory */
        factory<HttpClient> { FuelClientFactory().buildClient() }

        factory<BooruFactory> { BooruFactoryImpl(get()) }

        factory {
            val booruFactory = get<BooruFactory>()
            ArrayList<Class<out Booru>>().apply {
                add(Gelbooru::class.java)
                add(Safebooru::class.java)
                add(Rule34::class.java)
            }.map { booruFactory.buildBooru(it) }
        }

        /* Navigation used in start screen*/
        factory { StartScreenNavigator(setOf()) }
        /* Controller for the start's screen overflow menu*/
        factory { StartOverflowController() }

        factory { StartContentController(get()) }
    }
}
