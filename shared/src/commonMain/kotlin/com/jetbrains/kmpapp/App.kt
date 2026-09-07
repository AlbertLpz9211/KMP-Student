package com.jetbrains.kmpapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.jetbrains.kmpapp.screens.BookListContainer

@Composable
fun AppContent() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
    ) {
        Surface {
            BookListContainer(
                onBookClick = { id ->
                    println("Libro clickeado: $id")
                },
            )
        }
    }
}
