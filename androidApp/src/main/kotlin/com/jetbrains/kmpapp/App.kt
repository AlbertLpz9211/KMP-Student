package com.jetbrains.kmpapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jetbrains.kmpapp.screens.DetalleScreen
import kotlinx.serialization.Serializable
import com.jetbrains.kmpapp.screens.ListaScreen

@Serializable
object ListDestination

@Serializable
data class DetailDestination(val itemId: String)

@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(navController = navController, startDestination = ListDestination) {
                composable<ListDestination> {
                    ListaScreen(onItemClick = { id ->
                        navController.navigate(DetailDestination(id))
                    })
                }
                composable<DetailDestination> { backStackEntry ->
                    val destination = backStackEntry.toRoute<DetailDestination>()
                    DetalleScreen(
                        id = destination.itemId,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
