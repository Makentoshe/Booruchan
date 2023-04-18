package com.makentoshe.booruchan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.makentoshe.booruchan.library.navigation.BoorulistScreenNavigator
import com.makentoshe.booruchan.library.navigation.SplashScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.screen.splash.SplashScreen
import com.makentoshe.library.uikit.theme.BooruchanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainActivityComposeContent() }
    }
}

@Composable
private fun MainActivityComposeContent() {
    BooruchanTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.Splash.route) {
            splashScreen(navController = navController)
            boorulistScreen(navController = navController)
        }
    }
}


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
        TestScreen(navigator = navigator)
    }
}

@Composable
fun TestScreen(
    navigator: BoorulistScreenNavigator,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("BoorulistScreen")

        Button(onClick = {
            navigator.test()
        }) {
            Text(text = "Splash screen")
        }
    }
}
