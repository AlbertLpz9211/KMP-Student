package com.jetbrains.kmpapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.jetbrains.kmpapp.presentation.DetalleViewModel
import com.jetbrains.kmpapp.ui.theme.NeonCyan
import com.jetbrains.kmpapp.ui.theme.NeonPink
import com.jetbrains.kmpapp.ui.theme.NeonPurple
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    id: String,
    onBack: () -> Unit,
    viewModel: DetalleViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(id) {
        viewModel.cargarDetalle(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "ARTIST INFO", 
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp,
                            color = NeonCyan
                        )
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = NeonPink
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = NeonCyan,
                    navigationIconContentColor = NeonPink
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            Color(0xFF0F041A)
                        )
                    )
                )
        ) {
            when {
                state.cargando -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = NeonPink
                    )
                }
                state.error != null -> {
                    Text(
                        text = state.error?.uppercase() ?: "SYSTEM FAILURE",
                        modifier = Modifier.align(Alignment.Center),
                        color = NeonPink,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
                state.item != null -> {
                    val item = state.item!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        AsyncImage(
                            model = item.imagenUrl,
                            contentDescription = item.titulo,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                                .padding(16.dp)
                                .clip(RoundedCornerShape(24.dp))
                                .border(2.dp, Brush.linearGradient(listOf(NeonPink, NeonPurple)), RoundedCornerShape(24.dp)),
                            contentScale = ContentScale.Crop
                        )
                        
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(
                                text = item.titulo.uppercase(),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    letterSpacing = 1.sp
                                )
                            )
                            item.subtitulo?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = NeonCyan
                                )
                            }
                            
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 24.dp),
                                color = NeonPurple.copy(alpha = 0.3f)
                            )
                            
                            Text(
                                text = "METADATA",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 3.sp
                                ),
                                color = NeonPink,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            
                            val neonListItemColors = ListItemDefaults.colors(
                                containerColor = Color.Transparent,
                                headlineColor = Color.White,
                                supportingColor = NeonCyan.copy(alpha = 0.7f),
                                leadingIconColor = NeonPurple
                            )

                            item.precio?.let { precio ->
                                ListItem(
                                    headlineContent = { Text("CREDITS REQUIRED") },
                                    supportingContent = { Text("$precio ${item.moneda ?: ""}") },
                                    leadingContent = { Icon(Icons.Default.AttachMoney, null) },
                                    colors = neonListItemColors
                                )
                            }

                            item.urlPreview?.let { url ->
                                ListItem(
                                    headlineContent = { Text("AUDIO FREQUENCY") },
                                    supportingContent = { Text(url) },
                                    leadingContent = { Icon(Icons.Default.Audiotrack, null) },
                                    colors = neonListItemColors
                                )
                            }

                            item.pais?.let { pais ->
                                ListItem(
                                    headlineContent = { Text("ORIGIN SECTOR") },
                                    supportingContent = { Text(pais) },
                                    leadingContent = { Icon(Icons.Default.Public, null) },
                                    colors = neonListItemColors
                                )
                            }

                            item.descripcion?.let { desc ->
                                Text(
                                    text = "TRANSMISSION DATA",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 3.sp
                                    ),
                                    color = NeonPink,
                                    modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
                                )
                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
