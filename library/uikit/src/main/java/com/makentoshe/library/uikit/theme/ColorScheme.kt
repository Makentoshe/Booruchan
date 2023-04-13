package com.makentoshe.library.uikit.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val LightMaterialColorScheme = lightColors(
    primary = Black,
    onPrimary = White,

    secondary = White,
    onSecondary = Black,

    background = White,
    onBackground = Black
)

val LightColorScheme = BooruchanColorScheme(
    title = "Light color scheme",
    accent = Black,
    onAccent = White,
    opaque = BlackA30,
    dimmed = BlackA60,
    background = White,
    dashboard = Grey10,
    separator = Grey32,
)


val DarkColorScheme = BooruchanColorScheme(
    title = "Dark color scheme",
    accent = Black,
    onAccent = White,
    opaque = BlackA30,
    dimmed = BlackA60,
    background = White,
    dashboard = Grey10,
    separator = Grey32,
)

val DarkMaterialColorScheme = darkColors(
    primary = Black,
    onPrimary = White,

    secondary = White,
    onSecondary = Black,

    background = White,
    onBackground = Black
)


data class BooruchanColorScheme(
    internal val title: String,
    val accent: Color,
    val onAccent: Color,
    val opaque: Color,
    val dimmed: Color,
    val background: Color,
    val dashboard: Color,
    val separator: Color,
)

val BooruchanColorScheme.transparent: Color
    get() = Color.Transparent