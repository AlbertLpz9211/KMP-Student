package com.jetbrains.kmpapp.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jetbrains.kmpapp.presentation.list.MovieListState
import com.jetbrains.kmpapp.presentation.list.MovieListViewModel
import com.jetbrains.kmpapp.screens.components.EmptyState
import com.jetbrains.kmpapp.screens.components.ErrorState
import com.jetbrains.kmpapp.screens.components.LoadingState
import com.jetbrains.kmpapp.screens.components.MovieCard
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.compose.viewmodel.koinViewModel

/**
 * Pantalla de LISTA (Sesión 7): buscador con debounce, botón a Favoritas, cuadrícula con
 * los 4 estados, pull-to-refresh y PAGINACIÓN infinita (carga más al llegar al final).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    onMovieClick: (movieId: Int) -> Unit,
    onFavoritesClick: () -> Unit,
) {
    val viewModel = koinViewModel<MovieListViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    // El texto del buscador vive en la UI (respuesta inmediata al teclear); el debounce lo pone el VM.
    var texto by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CineKMP") },
                actions = {
                    IconButton(onClick = onFavoritesClick) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Ver favoritas")
                    }
                },
            )
        },
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it; viewModel.onQueryChange(it) },
                placeholder = { Text("Buscar películas…") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                trailingIcon = {
                    if (texto.isNotEmpty()) {
                        IconButton(onClick = { texto = ""; viewModel.onQueryChange("") }) {
                            Icon(Icons.Filled.Close, contentDescription = "Limpiar búsqueda")
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
            )

            PullToRefreshBox(
                isRefreshing = state.refrescando,
                onRefresh = { viewModel.refrescar() },
                modifier = Modifier.fillMaxSize(),
            ) {
                when {
                    state.cargando || state.buscando -> LoadingState()
                    state.error != null -> ErrorState(state.error!!, onReintentar = { viewModel.refrescar() })
                    state.peliculas.isEmpty() -> EmptyState(
                        if (state.enBusqueda) "Sin resultados para tu búsqueda" else "No hay películas para mostrar",
                    )
                    else -> MovieGrid(state, onMovieClick, onCargarMas = { viewModel.cargarMas() })
                }
            }
        }
    }
}

@Composable
private fun MovieGrid(
    state: MovieListState,
    onMovieClick: (Int) -> Unit,
    onCargarMas: () -> Unit,
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(state.peliculas, key = { it.id }) { pelicula ->
            MovieCard(pelicula, onClick = { onMovieClick(pelicula.id) })
        }
        // Fila completa con la ruedita de "cargando más" al paginar.
        if (state.cargandoMas) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }

    // PAGINACIÓN: observamos el último ítem visible; si nos acercamos al final (y no estamos
    // buscando), pedimos la siguiente página. snapshotFlow convierte el estado del scroll en Flow.
    LaunchedEffect(gridState, state.enBusqueda, state.peliculas.size) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0 }
            .distinctUntilChanged()
            .collect { ultimoVisible ->
                val total = state.peliculas.size
                if (!state.enBusqueda && total > 0 && ultimoVisible >= total - 4) {
                    onCargarMas()
                }
            }
    }
}
