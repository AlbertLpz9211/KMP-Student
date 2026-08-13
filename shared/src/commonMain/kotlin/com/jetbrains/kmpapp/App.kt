package com.jetbrains.kmpapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jetbrains.kmpapp.screens.detail.DetailScreen
import com.jetbrains.kmpapp.screens.list.ListScreen
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object ListDestination

@Serializable
data class DetailDestination(val objectId: String)

@Suppress("FunctionName")
@Composable
fun App() {
    val themeViewModel = koinViewModel<ThemeViewModel>()
    val isDarkModeOverride by themeViewModel.isDarkMode.collectAsState()
    
    val useDarkMode = isDarkModeOverride ?: isSystemInDarkTheme()

    MaterialTheme(
        colorScheme = if (useDarkMode) darkColorScheme() else lightColorScheme(),
    ) {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(navController = navController, startDestination = ListDestination) {
                composable<ListDestination> {
                    ListScreen(
                        navigateToDetails = { objectId ->
                            navController.navigate(DetailDestination(objectId))
                        },
                        isDarkMode = useDarkMode,
                        onToggleTheme = { themeViewModel.setDarkMode(!useDarkMode) }
                    )
                }
                composable<DetailDestination> { backStackEntry ->
                    DetailScreen(
                        objectId = backStackEntry.toRoute<DetailDestination>().objectId,
                        navigateBack = {
                            navController.popBackStack()
                        },
                    )
                }
            }
        }
    }
}
