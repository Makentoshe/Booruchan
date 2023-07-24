package com.makentoshe.booruchan.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makentoshe.booruchan.library.navigation.HomeScreenNavigator
import com.makentoshe.booruchan.library.navigation.SplashScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.screen.splash.SplashScreen

@Composable
internal fun MainActivityNavigationContent(navHostController: NavHostController) = NavHost(
    navController = navHostController,
    startDestination = Screen.Splash.route,
    builder = {
        splashScreen(navController = navHostController)
        homeScreen(navController = navHostController)
//        boorucontentScreen(navController = navHostController)
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
//        navigateToBoorucontentScreen = { booruContextUrl ->
//            val charset = StandardCharsets.UTF_8.toString()
//            val encodedBooruContextUrl = URLEncoder.encode(booruContextUrl, charset)
//            navController.navigate(Screen.Boorucontent.route(encodedBooruContextUrl))
//        }
    )

    composable(Screen.Home.route) {
        Text("Home screen")
    }
}
//
//private fun NavGraphBuilder.boorucontentScreen(navController: NavController) {
//    val navigator = BoorucontentScreenNavigator(
//        back = { navController.popBackStack() },
//    )
//
//    val screen = Screen.Boorucontent
//    composable(route = screen.route, arguments = screen.arguments) { entry ->
//        val encodedBooruContextUrl = entry.arguments?.getString(screen.booruContextUrlArgument.name)
//            ?: throw IllegalArgumentException("Argument ${screen.booruContextUrlArgument.name} invalid")
//
//        val charset = StandardCharsets.UTF_8.toString()
//        val booruContextUrl = URLDecoder.decode(encodedBooruContextUrl, charset)
//
//        BoorucontentScreen(navigator = navigator, booruContextUrl = booruContextUrl)
//    }
//}
