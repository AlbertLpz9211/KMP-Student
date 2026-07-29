package com.jetbrains.kmpapp.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * TEMA propio de CineKMP (Sesión 6). Un tema son 3 cosas en Material 3:
 *   - colorScheme  (paleta, con variante clara y oscura)
 *   - typography   (estilos de texto)
 *   - shapes       (esquinas; aquí usamos las de por defecto)
 *
 * Paleta "de cine": rojo teatro + dorado. Se define una vez y toda la app la hereda.
 */

private val RojoCine = Color(0xFFB00020)
private val DoradoCine = Color(0xFFE0A106)
private val NegroSala = Color(0xFF121212)

private val ColoresClaros = lightColorScheme(
    primary = RojoCine,
    secondary = DoradoCine,
    tertiary = DoradoCine,
)

private val ColoresOscuros = darkColorScheme(
    primary = Color(0xFFFF6E6E),
    secondary = DoradoCine,
    tertiary = DoradoCine,
    background = NegroSala,
    surface = NegroSala,
)

// Tipografía: partimos de la de por defecto y "engordamos" los títulos.
private val TipografiaCine = Typography().run {
    copy(
        headlineSmall = headlineSmall.copy(fontWeight = FontWeight.Bold),
        titleMedium = titleMedium.copy(fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
    )
}

/** Envuelve la app. Usar en App.kt en vez de `MaterialTheme` pelón. */
@Composable
fun CineTheme(
    oscuro: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (oscuro) ColoresOscuros else ColoresClaros,
        typography = TipografiaCine,
        content = content,
    )
}
