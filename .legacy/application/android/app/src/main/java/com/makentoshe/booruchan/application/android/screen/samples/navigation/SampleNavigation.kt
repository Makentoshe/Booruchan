package com.makentoshe.booruchan.application.android.screen.samples.navigation

import ru.terrakok.cicerone.Router

class SampleNavigation(private val router: Router) {

    fun closeScreen() {
        router.exit()
    }
}