package com.jetbrains.kmpapp.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jetbrains.kmpapp.domain.model.Movie
import com.jetbrains.kmpapp.presentation.list.MovieListViewModel
import com.jetbrains.kmpapp.screens.components.EmptyState
import com.jetbrains.kmpapp.screens.components.ErrorState
import com.jetbrains.kmpapp.screens.components.LoadingState
import com.jetbrains.kmpapp.screens.components.MoviePoster
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt

/**
 * Pantalla de LISTA (Sesión 6): cuadrícula de pósters con los 4 estados posibles
 * (cargando · error+reintentar · vacío · datos) y pull-to-refresh para recargar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    onMovieClick: (movieId: Int) -> Unit,
) {
    val viewModel = koinViewModel<MovieListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    // PullToRefreshBox: al arrastrar hacia abajo llama a onRefresh; muestra su ruedita según isRefreshing.
    PullToRefreshBox(
        isRefreshing = state.refrescando,
        onRefresh = { viewModel.refrescar() },
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
    ) {
        when {
            state.cargando -> LoadingState()
            state.error != null -> ErrorState(state.error!!, onReintentar = { viewModel.refrescar() })
            state.peliculas.isEmpty() -> EmptyState("No hay películas para mostrar")
            else -> MovieGrid(state.peliculas, onMovieClick)
        }
    }
}

@Composable
private fun MovieGrid(
    peliculas: List<Movie>,
    onMovieClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp), // se adapta al ancho: 2, 3, 4 columnas…
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(peliculas, key = { it.id }) { pelicula ->
            MovieCard(pelicula, onClick = { onMovieClick(pelicula.id) })
        }
    }
}

@Composable
private fun MovieCard(pelicula: Movie, onClick: () -> Unit) {
    Column(
        Modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
    ) {
        MoviePoster(
            url = pelicula.posterUrl,
            contentDescription = pelicula.titulo,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)          // proporción típica de un póster
                .clip(RoundedCornerShape(12.dp)),
        )
        Spacer(Modifier.height(6.dp))
        Text(
            pelicula.titulo,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            "★ ${(pelicula.rating * 10).roundToInt() / 10.0}  ·  ${pelicula.anio}",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
