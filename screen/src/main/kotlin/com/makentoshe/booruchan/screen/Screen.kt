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

    object Home: Screen {
        override val route: String = "HomeScreen"
    }

    object Source: Screen {
        private val sourceIdArgumentName = "SourceId"
        val sourceIdArgument = navArgument(sourceIdArgumentName) {
            type = NavType.StringType
        }

        override val arguments: List<NamedNavArgument> = listOf(sourceIdArgument)

        override val route: String = "SourceScreen/{$sourceIdArgumentName}"

        fun route(sourceId: String) = "SourceScreen/$sourceId"
    }
}
