package com.makentoshe.booruchan.library.navigation.controller

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavController
import androidx.navigation.NavDestination

@Composable
fun NavController.setOnDisposableDestinationChangedListenerEffect(
    action: (controller: NavController, destination: NavDestination, arguments: Bundle?) -> Unit
) {
    DisposableEffect(key1 = this, effect = {
        val listener = NavController.OnDestinationChangedListener { c, d, a -> action(c, d, a) }
        addOnDestinationChangedListener(listener)
        onDispose { removeOnDestinationChangedListener(listener) }
    })
}
