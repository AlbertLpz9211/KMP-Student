package com.jetbrains.kmpapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetalleScreen(
    id: String,
    onBack: () -> Unit,
    viewModel: DetalleViewModel = koinViewModel()
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onBack) {
            Text("Volver")
        }
        Text("Detalle del Item: $id")
        // Aquí se mostraría el detalle cargado desde el ViewModel
    }
}
