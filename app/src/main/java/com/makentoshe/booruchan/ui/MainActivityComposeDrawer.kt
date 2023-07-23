package com.makentoshe.booruchan.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.makentoshe.booruchan.screen.Screen
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Modifier
import com.makentoshe.library.uikit.foundation.PrimaryText
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
internal fun rememberDrawerState(navController: NavController) = rememberDrawerState(
    initialValue = DrawerValue.Closed,
    confirmStateChange = { confirmDrawerStateChange(it, navController) }
)

private fun confirmDrawerStateChange(value: DrawerValue, navController: NavController): Boolean {
//    return navController.currentDestination?.route != Screen.Splash.route
    return true
}

@Composable
internal fun MainActivityDrawerContent() = Column(
    modifier = Modifier.fillMaxSize().background(BooruchanTheme.colors.background),
) {
    PrimaryText("Drawer content")
}