package com.makentoshe.booruchan.application.android.screen.start.navigation

import com.makentoshe.booruchan.application.android.screen.booru.navigation.BooruScreen
import com.makentoshe.booruchan.core.context.BooruContext
import ru.terrakok.cicerone.Router

class StartNavigation(private val router: Router) {

    fun navigateToBooruScreen(context: BooruContext) {
        router.navigateTo(BooruScreen(context))
    }
}