package com.lek.spontan.android.presentation.shared.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = DeepOrange200,
    primaryVariant = DeepOrange700,
    secondary = Gray200
)

private val LightColorPalette = lightColors(
    primary = DeepOrange500,
    primaryVariant = DeepOrange700,
    secondary = Gray200,
    onPrimary = Gray900
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
        typography = SpontanTypography,
        shapes = Shapes,
        content = content
    )
}
