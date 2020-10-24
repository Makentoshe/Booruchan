package com.makentoshe.booruchan.application.android.start.navigation

import context.BooruContext
import ru.terrakok.cicerone.Router

class StartNavigation(private val router: Router) {

    fun navigateToBooruScreen(context: BooruContext) {
        println(context.title)
        println(router)
    }
}