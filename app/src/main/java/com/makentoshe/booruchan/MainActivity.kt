package com.makentoshe.booruchan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.makentoshe.booruchan.library.navigation.BoorulistScreenNavigator
import com.makentoshe.booruchan.library.navigation.SplashScreenNavigator
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.booruchan.screen.splash.SplashScreen
import com.makentoshe.booruchan.ui.MainActivityComposeContent
import com.makentoshe.library.uikit.theme.BooruchanTheme
import com.makentoshe.screen.boorulist.BoorulistScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainActivityComposeContent() }
    }
}
