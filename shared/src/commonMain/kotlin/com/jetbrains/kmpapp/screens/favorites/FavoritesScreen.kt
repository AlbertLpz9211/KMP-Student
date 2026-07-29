package com.jetbrains.kmpapp.screens.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jetbrains.kmpapp.presentation.favorites.FavoritesViewModel
import com.jetbrains.kmpapp.screens.components.EmptyState
import com.jetbrains.kmpapp.screens.components.MovieCard
import org.koin.compose.viewmodel.koinViewModel

/**
 * Pantalla de FAVORITAS (Sesión 7): solo las películas con el corazón activado.
 * Se actualiza en tiempo real: si quitas un favorito en el detalle, desaparece de aquí solo,
 * porque observamos el mismo Flow de la base de datos.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onMovieClick: (movieId: Int) -> Unit,
    onBack: () -> Unit,
) {
    val viewModel = koinViewModel<FavoritesViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoritas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
            )
        },
    ) { padding ->
        if (state.favoritas.isEmpty()) {
            EmptyState(
                "Aún no tienes favoritas.\nToca el corazón ❤ en una película.",
                Modifier.padding(padding),
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(8.dp),
            ) {
                items(state.favoritas, key = { it.id }) { pelicula ->
                    MovieCard(pelicula, onClick = { onMovieClick(pelicula.id) })
                }
            }
        }
    }
}
