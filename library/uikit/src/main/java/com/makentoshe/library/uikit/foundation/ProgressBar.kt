package com.makentoshe.library.uikit.foundation

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
fun IndeterminateProgressBar(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 4.dp,
) = CircularProgressIndicator(
    modifier = modifier,
    strokeWidth = strokeWidth,
    color = BooruchanTheme.colors.accent
)

@Composable
fun IndeterminateLinearProgressBar(
    modifier: Modifier = Modifier
) = LinearProgressIndicator(
    modifier = modifier,
    color = BooruchanTheme.colors.accent
)
