package com.makentoshe.booruchan.screen

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed interface Screen {
    val route: String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

}

data class Argument(val name: String)