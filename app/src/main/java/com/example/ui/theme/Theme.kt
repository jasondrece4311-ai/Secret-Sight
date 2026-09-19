package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2ECC71),
    onPrimary = Color(0xFF042111),
    secondary = Color(0xFFF1C40F),
    onSecondary = Color(0xFF2C2200),
    surface = Color(0xFF14241C),
    onSurface = Color(0xFFE8F5E9),
    surfaceVariant = Color(0xFF20382C),
    onSurfaceVariant = Color(0xFFC3D9CC),
    background = Color(0xFF0D1B14),
    onBackground = Color(0xFFE8F5E9)
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldPrimary,
    onPrimary = Color.White,
    secondary = AmberSecondary,
    onSecondary = Color.Black,
    surface = Color(0xFFF7FBF8),
    onSurface = Color(0xFF1A2720),
    surfaceVariant = Color(0xFFE2EDE5),
    onSurfaceVariant = Color(0xFF3B4E43),
    background = Color(0xFFF2F7F4),
    onBackground = Color(0xFF1A2720)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent crafted game atmosphere
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
