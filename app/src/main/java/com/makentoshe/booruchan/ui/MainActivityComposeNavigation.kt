package com.makentoshe.booruchan.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makentoshe.booruchan.library.navigation.HomeScreenNavigator
import com.makentoshe.booruchan.library.navigation.SourceScreenNavigator
import com.makentoshe.booruchan.library.navigation.SplashScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.screen.source.SourceScreen
import com.makentoshe.booruchan.screen.splash.SplashScreen
import com.makentoshe.screen.boorulist.HomeScreen

@Composable
internal fun MainActivityNavigationContent(navHostController: NavHostController) = NavHost(
    navController = navHostController,
    startDestination = Screen.Splash.route,
    builder = {
        splashScreen(navController = navHostController)
        homeScreen(navController = navHostController)
        sourceScreen(navController = navHostController)
    }
)

private fun NavGraphBuilder.splashScreen(navController: NavController) {
    val navigator = SplashScreenNavigator(
        navigateToHomeScreen = {
            navController.navigate(Screen.Home.route)
        }
    )

    composable(Screen.Splash.route) {
        SplashScreen(navigator = navigator)
    }
}

private fun NavGraphBuilder.homeScreen(navController: NavController) {
    val navigator = HomeScreenNavigator(
        navigateToSourceScreen = { sourceId ->
            navController.navigate(Screen.Source.route(sourceId))
        }
    )

    composable(Screen.Home.route) {
        HomeScreen(navigator = navigator)
    }
}

private fun NavGraphBuilder.sourceScreen(navController: NavController) {
    val navigator = SourceScreenNavigator(
        back = { navController.popBackStack() },
    )

    val screen = Screen.Source
    composable(route = screen.route, arguments = screen.arguments) { entry ->
        val sourceId = entry.arguments?.getString(screen.sourceIdArgument.name)!!
        SourceScreen(navigator = navigator, sourceId = sourceId)
    }
}
