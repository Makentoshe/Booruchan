package com.makentoshe.booruchan.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makentoshe.booruchan.library.navigation.BoorulistScreenNavigator
import com.makentoshe.booruchan.library.navigation.SplashScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.screen.splash.SplashScreen
import com.makentoshe.screen.boorulist.BoorulistScreen

@Composable
internal fun MainActivityNavigationContent(navHostController: NavHostController) = NavHost(
    navController = navHostController,
    startDestination = Screen.Boorulist.route,
    builder = {
        boorulistScreen(navController = navHostController)
    }
)

private fun NavGraphBuilder.boorulistScreen(navController: NavController) {
    val navigator = BoorulistScreenNavigator(
        navigateToBoorucontentScreen = {
            println("Navigate to Boorucontent")
        }
    )

    composable(Screen.Boorulist.route) {
        BoorulistScreen(navigator = navigator)
    }
}
