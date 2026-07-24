package com.jetbrains.kmpapp

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jetbrains.kmpapp.screens.detail.DetailScreen
import com.jetbrains.kmpapp.screens.favorites.FavoritesScreen
import com.jetbrains.kmpapp.screens.list.ListScreen
import com.jetbrains.kmpapp.theme.CineTheme
import kotlinx.serialization.Serializable

/**
 * DESTINOS de navegación (type-safe con Navigation Compose). Cada destino es un objeto/clase
 * @Serializable; los argumentos viajan como propiedades (aquí, el id de la película).
 */
@Serializable
object ListDestination

@Serializable
object FavoritesDestination

@Serializable
data class DetailDestination(val movieId: Int)

/**
 * Punto de entrada de la UI COMPARTIDA (misma función para Android e iOS).
 * En la Sesión 5 solo navegamos Lista → Detalle; en la Sesión 7 añadiremos Favoritas.
 */
@Composable
fun App() {
    CineTheme {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(navController = navController, startDestination = ListDestination) {
                composable<ListDestination> {
                    ListScreen(
                        onMovieClick = { movieId ->
                            navController.navigate(DetailDestination(movieId))
                        },
                        onFavoritesClick = { navController.navigate(FavoritesDestination) },
                    )
                }
                composable<FavoritesDestination> {
                    FavoritesScreen(
                        onMovieClick = { movieId ->
                            navController.navigate(DetailDestination(movieId))
                        },
                        onBack = { navController.popBackStack() },
                    )
                }
                composable<DetailDestination> { backStackEntry ->
                    DetailScreen(
                        movieId = backStackEntry.toRoute<DetailDestination>().movieId,
                        onBack = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}
