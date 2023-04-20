package com.makentoshe.screen.boorulist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makentoshe.booruchan.library.navigation.BoorulistScreenNavigator

@Composable
fun BoorulistScreen(
    navigator: BoorulistScreenNavigator,
) = Scaffold(
    content = { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            BoorulistScreenContent(navigator = navigator)
        }
    }
)

@Composable
private fun BoorulistScreenContent(
    navigator: BoorulistScreenNavigator,
) {
    Text("Boorulist")
}