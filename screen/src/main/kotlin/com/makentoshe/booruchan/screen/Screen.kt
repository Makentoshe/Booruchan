package com.makentoshe.booruchan.screen

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed interface Screen {
    val route: String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    object Splash : Screen {
        override val route: String = "SplashScreen"
    }

    object Boorulist : Screen {
        override val route: String = "BoorulistScreen"
    }

    object Boorucontent : Screen {
        private val booruContextUrlArgumentName = "BooruContextUrl"
        val booruContextUrlArgument = navArgument(booruContextUrlArgumentName) {
            type = NavType.StringType
        }

        override val arguments: List<NamedNavArgument> = listOf(booruContextUrlArgument)

        override val route: String = "BoorucontentScreen/{$booruContextUrlArgumentName}"

        fun route(booruContextUrl: String) = "BoorucontentScreen/$booruContextUrl"
    }
}

data class Argument(val name: String)