package com.makentoshe.booruchan.ui

import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.makentoshe.booruchan.library.navigation.controller.setOnDisposableDestinationChangedListenerEffect
import com.makentoshe.booruchan.screen.Screen
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
internal fun MainActivityComposeContent() = BooruchanTheme {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(navController = navController)

    // Disable drawer content on splash screen
    var drawerGesturesEnabled by remember { mutableStateOf(false) }
    navController.setOnDisposableDestinationChangedListenerEffect { _, destination, _ ->
        drawerGesturesEnabled = destination.route != Screen.Splash.route
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerGesturesEnabled,
        drawerContent = { MainActivityDrawerContent() },
        content = { MainActivityNavigationContent(navHostController = navController) }
    )
}
