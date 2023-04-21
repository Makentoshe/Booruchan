package com.makentoshe.library.uikit.foundation

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
fun IndeterminateProgressBar(
    modifier: Modifier = Modifier
) = CircularProgressIndicator(
    modifier = modifier,
    color = BooruchanTheme.colors.accent
)

@Composable
fun IndeterminateLinearProgressBar(
    modifier: Modifier = Modifier
) = LinearProgressIndicator(
    modifier = modifier,
    color = BooruchanTheme.colors.accent
)
