package com.example.simala.ui.theme // FIXED: Match your MainActivity package

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Using standard Color values to prevent "Unresolved reference" crashes
// until your Color.kt is fully synced
private val DarkColorScheme = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFFD0BCFF),
    secondary = androidx.compose.ui.graphics.Color(0xFFCCC2DC),
    tertiary = androidx.compose.ui.graphics.Color(0xFFEFB8C8)
)

private val LightColorScheme = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF6650a4),
    secondary = androidx.compose.ui.graphics.Color(0xFF625b71),
    tertiary = androidx.compose.ui.graphics.Color(0xFF7D5260)
)

@Composable
fun VeraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Disabled dynamicColor by default to stabilize the "Measure" phase crash
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Ensure Typography is defined in your Type.kt in the same package
        content = content
    )
}