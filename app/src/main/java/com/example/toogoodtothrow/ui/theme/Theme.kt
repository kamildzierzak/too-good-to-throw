package com.example.toogoodtothrow.ui.theme

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

private val LightColorScheme = lightColorScheme(
    primary = FreshGreenLight,
    onPrimary = Color.White,
    primaryContainer = FreshGreenLight,
    onPrimaryContainer = Color.White,

    secondary = Color(0xFF1565C0),
    tertiaryContainer = WarningOrangeLight,
    onTertiaryContainer = Color(0xFF231A00),

    errorContainer = ExpiredRedLight,
    onErrorContainer = Color(0xFF410002),

    surfaceVariant = DiscardGreyLight,
    onSurfaceVariant = Color(0xFF1D1B20),
)

private val DarkColorScheme = darkColorScheme(
    primary = FreshGreenDark,
    onPrimary = Color(0xFF00391D),
    primaryContainer = FreshGreenDark,
    onPrimaryContainer = Color.Black,

    secondary = Color(0xFF90CAF9),
    tertiaryContainer = WarningOrangeDark,
    onTertiaryContainer = Color(0xFFFFF3E0),

    errorContainer = ExpiredRedDark,
    onErrorContainer = Color(0xFFFFDAD6),

    surfaceVariant = DiscardGreyDark,
    onSurfaceVariant = Color(0xFFC4C3C8),
)


@Composable
fun TooGoodToThrowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}
