package com.makentoshe.booruchan.screen.source

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.makentoshe.booruchan.library.navigation.SourceScreenNavigator

@Composable
fun SourceScreen(
    navigator: SourceScreenNavigator,
    sourceId: String
) {
    Text(sourceId)
}