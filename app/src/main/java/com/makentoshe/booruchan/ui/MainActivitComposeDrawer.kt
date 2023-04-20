package com.makentoshe.booruchan.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.makentoshe.booruchan.screen.Screen
import androidx.compose.material3.rememberDrawerState

@Composable
internal fun rememberDrawerState(navController: NavController) = rememberDrawerState(
    initialValue = DrawerValue.Closed,
    confirmStateChange = { confirmDrawerStateChange(it, navController) }
)

private fun confirmDrawerStateChange(value: DrawerValue, navController: NavController): Boolean {
    return navController.currentDestination?.route != Screen.Splash.route
}

@Composable
internal fun ColumnScope.MainActivityDrawerContent() {
    Text("Drawer content")
}