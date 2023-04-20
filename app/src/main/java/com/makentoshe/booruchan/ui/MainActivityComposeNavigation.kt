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
    startDestination = Screen.Splash.route,
    builder = {
        splashScreen(navController = navHostController)
        boorulistScreen(navController = navHostController)
    }
)

private fun NavGraphBuilder.splashScreen(navController: NavController) {
    val navigator = SplashScreenNavigator(
        navigateToBoorulistScreen = {
            navController.navigate(Screen.Boorulist.route)
        }
    )

    composable(Screen.Splash.route) {
        SplashScreen(navigator)
    }
}

private fun NavGraphBuilder.boorulistScreen(navController: NavController) {
    val navigator = BoorulistScreenNavigator(
        test = {
            navController.navigate(Screen.Splash.route)
        }
    )

    composable(Screen.Boorulist.route) {
        BoorulistScreen(navigator = navigator)
    }
}
