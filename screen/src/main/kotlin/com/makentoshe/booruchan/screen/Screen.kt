package com.makentoshe.booruchan.screen

sealed interface Screen {
    val route: String

    object Splash : Screen {
        override val route: String = "SplashScreen"
    }

    object Boorulist : Screen {
        override val route: String = "BoorulistScreen"
    }
}
