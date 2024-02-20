package com.hocheol.movieapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.hocheol.movieapp.ui.theme.color.ColorSet

private val LocalColors = staticCompositionLocalOf { ColorSet.Red.LightColors }

@Composable
fun MovieAppTheme(
    myColors: ColorSet = ColorSet.Red,
    shapes: Shapes = Shapes,
    typography: Typography = Typography,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when {
        darkTheme -> myColors.DarkColors
        else -> myColors.LightColors
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(
            colorScheme = colors.material,
            shapes = shapes,
            typography = typography,
            content = content
        )
    }
}