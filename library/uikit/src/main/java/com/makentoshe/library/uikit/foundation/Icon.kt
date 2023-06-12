package com.makentoshe.library.uikit.foundation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makentoshe.library.uikit.theme.BooruchanTheme

@Composable
fun MagnifyIcon(
    modifier: Modifier = Modifier,
) = Icon(
    modifier = modifier,
    tint = BooruchanTheme.colors.accent,
    imageVector = Icons.Default.Search,
    contentDescription = null,
)

@Composable
fun ArrowBackIcon(
    modifier: Modifier = Modifier,
) = Icon(
    modifier = modifier,
    tint = BooruchanTheme.colors.accent,
    imageVector = Icons.Default.ArrowBack,
    contentDescription = null,
)
