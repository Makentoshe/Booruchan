package com.makentoshe.booruchan.screen.boorucontent.ui.foundation.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.makentoshe.library.uikit.foundation.IndeterminateProgressBar

@Composable
internal fun BoorucontentLoadingLayout() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
) {
    IndeterminateProgressBar()
}
