package com.makentoshe.booruchan.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makentoshe.booruchan.library.navigation.SplashScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
internal fun MainActivityNavigationContent(navHostController: NavHostController) = NavHost(
    navController = navHostController,
    startDestination = Screen.Splash.route,
    builder = {
        splashScreen(navController = navHostController)
//        boorulistScreen(navController = navHostController)
//        boorucontentScreen(navController = navHostController)
    }
)

private fun NavGraphBuilder.splashScreen(navController: NavController) {
    val navigator = SplashScreenNavigator(
        navigateToBoorulistScreen = {
//            navController.navigate(Screen.Boorulist.route)
        }
    )

    composable(Screen.Splash.route) {
        Text("Splash screen")
//        SplashScreen(navigator = navigator)
    }
}

//private fun NavGraphBuilder.boorulistScreen(navController: NavController) {
//    val navigator = BoorulistScreenNavigator(
//        navigateToBoorucontentScreen = { booruContextUrl ->
//            val charset = StandardCharsets.UTF_8.toString()
//            val encodedBooruContextUrl = URLEncoder.encode(booruContextUrl, charset)
//            navController.navigate(Screen.Boorucontent.route(encodedBooruContextUrl))
//        }
//    )
//
//    composable(Screen.Boorulist.route) {
//        BoorulistScreen(navigator = navigator)
//    }
//}
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
