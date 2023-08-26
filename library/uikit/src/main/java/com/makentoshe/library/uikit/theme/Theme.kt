package com.makentoshe.library.uikit.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.makentoshe.library.uikit.theme.BooruchanTheme.isInDarkTheme

object BooruchanTheme {

    internal val LocalCompositionIsInDarkTheme =
        staticCompositionLocalOf { false }
    val isInDarkTheme: Boolean
        @Composable get() = LocalCompositionIsInDarkTheme.current

    internal val LocalCompositionMaterialColors =
        staticCompositionLocalOf { LightMaterialColorScheme }
    val materialColors: ColorScheme
        @Composable get() = LocalCompositionMaterialColors.current

    internal val LocalCompositionColors =
        staticCompositionLocalOf { LightColorScheme }
    val colors: BooruchanColorScheme
        @Composable get() = LocalCompositionColors.current

    internal val LocalCompositionShapes =
        staticCompositionLocalOf { DefaultBooruchanShapesScheme }
    val shapes: BooruchanShapeScheme
        @Composable get() = LocalCompositionShapes.current

    internal val LocalCompositionMaterialTypography =
        staticCompositionLocalOf { Typography() }
    internal val MaterialTypography: Typography
        @Composable get() = LocalCompositionMaterialTypography.current

    internal val LocalCompositionTypography =
        staticCompositionLocalOf { DefaultBooruchanTypography }
    val typography: BooruchanTypographyScheme
        @Composable get() = LocalCompositionTypography.current
}

@Composable
fun BooruchanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) = CompositionLocalProvider(BooruchanTheme.LocalCompositionIsInDarkTheme provides darkTheme) {
    CompositionLocalProvider(
        BooruchanTheme.LocalCompositionMaterialColors provides if (isInDarkTheme) DarkMaterialColorScheme else LightMaterialColorScheme,
        BooruchanTheme.LocalCompositionColors provides if (isInDarkTheme) DarkColorScheme else LightColorScheme,
    ) {
        BooruchanTheme(content)
    }
}

@Composable
private fun BooruchanTheme(content: @Composable () -> Unit) {
    val colors = BooruchanTheme.materialColors
    val darkTheme = isInDarkTheme
    val view = LocalView.current
    if (!view.isInEditMode) SideEffect {
        (view.context as Activity).window.statusBarColor = colors.primary.toArgb()
        ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
    }

    // use MaterialTheme for backward compatibility
    MaterialTheme(
        colorScheme = BooruchanTheme.materialColors,
        typography = BooruchanTheme.MaterialTypography,
        content = content
    )
}