package com.lek.spontan.android.presentation.shared.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = DeepOrange200,
    primaryVariant = DeepOrange700,
    secondary = Gray200
)

private val LightColorPalette = lightColors(
    primary = DeepOrange500,
    primaryVariant = DeepOrange700,
    secondary = Gray200,
    onSecondary = Gray900,
    onBackground = Gray900,
    onSurface = Gray900,
    background = Gray50,
    surface = Gray50,
    onPrimary = Gray50
)

@Composable
fun SpontanTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}