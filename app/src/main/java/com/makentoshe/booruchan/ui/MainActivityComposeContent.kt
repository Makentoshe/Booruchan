package com.makentoshe.booruchan.ui

import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.makentoshe.booruchan.library.logging.screenLogInfo
import com.makentoshe.booruchan.library.navigation.SplashScreenNavigator
import com.makentoshe.booruchan.screen.splash.SplashScreen
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
internal fun MainActivityComposeContent() = BooruchanTheme {
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = rememberDrawerState(navController = navController),
        drawerContent = { MainActivityDrawerContent() },
        content = { MainActivityNavigationContent(navHostController = navController) }
    )

    screenLogInfo("MainActivity", "OnCreateCompose")
}
