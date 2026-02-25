package com.pjdev.calisthenicslevel.presentation.theme

import android.app.Activity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val Gold = Color(0xFFF3C969)
private val GoldMuted = Color(0xFFB99035)
private val Graphite = Color(0xFF0A0D12)
private val Carbon = Color(0xFF121720)
private val Slate = Color(0xFF1A2230)
private val Steel = Color(0xFF9AA5B5)
private val Mist = Color(0xFFCDD4DE)

private val DarkColors = darkColorScheme(
    primary = Gold,
    onPrimary = Color(0xFF1B1200),
    secondary = Steel,
    onSecondary = Graphite,
    tertiary = GoldMuted,
    onTertiary = Color.Black,
    background = Graphite,
    onBackground = Color(0xFFF5F7FA),
    surface = Carbon,
    onSurface = Color(0xFFF5F7FA),
    surfaceVariant = Slate,
    onSurfaceVariant = Mist,
    outline = Color(0xFF303B4D),
    error = Color(0xFFFF6E6E)
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF9E741A),
    onPrimary = Color.White,
    secondary = Color(0xFF47556D),
    onSecondary = Color.White,
    tertiary = Gold,
    background = Color(0xFFF4F6FA),
    onBackground = Color(0xFF0D121A),
    surface = Color.White,
    onSurface = Color(0xFF0D121A),
    surfaceVariant = Color(0xFFE8EBF1),
    onSurfaceVariant = Color(0xFF47556D),
    outline = Color(0xFFD2D8E1),
    error = Color(0xFFB3261E)
)

val AppShapes = Shapes(
    small = RoundedCornerShape(14.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(24.dp)
)

@Composable
fun CalisthenicsLevelTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            window.statusBarColor = colors.background.toArgb()
            window.navigationBarColor = colors.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

private fun ColorScheme.toArgb() = android.graphics.Color.argb(
    (alpha * 255).toInt(),
    (red * 255).toInt(),
    (green * 255).toInt(),
    (blue * 255).toInt()
)
