package com.jetbrains.kmpapp.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jetbrains.kmpapp.presentation.detail.MovieDetailViewModel
import com.jetbrains.kmpapp.screens.components.EmptyState
import com.jetbrains.kmpapp.screens.components.LoadingState
import com.jetbrains.kmpapp.screens.components.MoviePoster
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt

/**
 * Pantalla de DETALLE (Sesión 6): imagen, datos y BOTÓN DE FAVORITO real.
 * El corazón se rellena/vacía en tiempo real porque `state.pelicula` viene de la DB (Flow):
 * al pulsar, guardamos en SQLite → el Flow emite → la UI se redibuja sola.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movieId: Int,
    onBack: () -> Unit,
) {
    val viewModel = koinViewModel<MovieDetailViewModel>()
    LaunchedEffect(movieId) { viewModel.cargar(movieId) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pelicula = state.pelicula

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pelicula?.titulo ?: "Detalle") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    // El botón de favorito solo aparece cuando ya tenemos la película.
                    if (pelicula != null) {
                        IconButton(onClick = { viewModel.alternarFavorito() }) {
                            Icon(
                                imageVector = if (pelicula.esFavorita) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = if (pelicula.esFavorita) "Quitar de favoritas" else "Añadir a favoritas",
                            )
                        }
                    }
                },
            )
        },
    ) { padding ->
        when {
            state.cargando -> LoadingState(Modifier.padding(padding))
            pelicula == null -> EmptyState(state.error ?: "No se encontró la película", Modifier.padding(padding))
            else -> Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Imagen ancha: usamos el backdrop de la red si llegó; si no, el póster.
                MoviePoster(
                    url = state.detalle?.backdropUrl ?: pelicula.posterUrl,
                    contentDescription = pelicula.titulo,
                    modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f),
                )
                Column(Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(pelicula.titulo, style = MaterialTheme.typography.headlineSmall)
                    Text(
                        "★ ${(pelicula.rating * 10).roundToInt() / 10.0}  ·  ${pelicula.anio}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    state.detalle?.let { d ->
                        d.tagline?.let { Text("“$it”", style = MaterialTheme.typography.bodyMedium) }
                        if (d.generos.isNotEmpty()) Text("Géneros: ${d.generos.joinToString()}")
                        d.duracionMin?.let { Text("Duración: $it min") }
                    }
                    Text(pelicula.overview, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
