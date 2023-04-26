package com.makentoshe.booruchan.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makentoshe.booruchan.library.navigation.BoorucontentScreenNavigator
import com.makentoshe.booruchan.library.navigation.BoorulistScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.screen.boorucontent.BoorucontentScreen
import com.makentoshe.screen.boorulist.BoorulistScreen
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
internal fun MainActivityNavigationContent(navHostController: NavHostController) = NavHost(
    navController = navHostController,
    startDestination = Screen.Boorulist.route,
    builder = {
        boorulistScreen(navController = navHostController)
        boorucontentScreen(navController = navHostController)
    }
)

private fun NavGraphBuilder.boorulistScreen(navController: NavController) {
    val navigator = BoorulistScreenNavigator(
        navigateToBoorucontentScreen = { booruContextUrl ->
            val charset = StandardCharsets.UTF_8.toString()
            val encodedBooruContextUrl = URLEncoder.encode(booruContextUrl, charset)
            navController.navigate(Screen.Boorucontent.route(encodedBooruContextUrl))
        }
    )

    composable(Screen.Boorulist.route) {
        BoorulistScreen(navigator = navigator)
    }
}

private fun NavGraphBuilder.boorucontentScreen(navController: NavController) {
    val navigator = BoorucontentScreenNavigator(
        test = {
            println("test navigation")
        }
    )

    val screen = Screen.Boorucontent
    composable(route = screen.route, arguments = screen.arguments) { entry ->
        val encodedBooruContextUrl = entry.arguments?.getString(screen.booruContextUrlArgument.name)
            ?: throw IllegalArgumentException("Argument ${screen.booruContextUrlArgument.name} invalid")

        val charset = StandardCharsets.UTF_8.toString()
        val booruContextUrl = URLDecoder.decode(encodedBooruContextUrl, charset)

        BoorucontentScreen(navigator = navigator, booruContextUrl = booruContextUrl)
    }
}
