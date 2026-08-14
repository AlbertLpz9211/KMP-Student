package com.jetbrains.kmpapp.screens.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.jetbrains.kmpapp.domain.model.BookStatus
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.ItemDetalle
import com.jetbrains.kmpapp.screens.EmptyScreenContent
import cinekmp.shared.generated.resources.Res
import cinekmp.shared.generated.resources.back
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Suppress("FunctionName")
@Composable
fun DetailScreen(
    objectId: String,
    navigateBack: () -> Unit,
) {
    val viewModel = koinViewModel<DetailViewModel>()

    val itemDetalle by viewModel.getObject(objectId).collectAsStateWithLifecycle(initialValue = null)
    AnimatedContent(
        targetState = itemDetalle,
        label = "DetailAnimation"
    ) { targetDetail ->
        if (targetDetail != null) {
            ObjectDetails(
                itemDetalle = targetDetail, 
                onBackClick = navigateBack,
                onToggleFavorite = { viewModel.toggleFavorite(objectId, !targetDetail.item.isFavorite) },
                onStatusChange = { status -> viewModel.updateStatus(objectId, status) },
                onRelatedClick = { id -> 
                    // This would need navigation
                }
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("FunctionName")
@Composable
private fun ObjectDetails(
    itemDetalle: ItemDetalle,
    onBackClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onStatusChange: (BookStatus) -> Unit,
    onRelatedClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val item = itemDetalle.item
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(item.titulo, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(Res.string.back))
                    }
                },
                actions = {
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Toggle Favorite",
                            tint = if (item.isFavorite) Color.Red else LocalContentColor.current
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                )
            )
        },
    ) { paddingValues ->
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues),
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                AsyncImage(
                    model = item.imagenUrl,
                    contentDescription = item.titulo,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().background(Color.LightGray),
                )
                
                if (item.metrica != null) {
                    Surface(
                        modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star, 
                                contentDescription = null, 
                                modifier = Modifier.size(16.dp),
                                tint = Color(0xFFFFD700) // Gold color for star
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = item.metrica.toString(), 
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            SelectionContainer {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        item.titulo, 
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    if (!item.subtitulo.isNullOrBlank()) {
                        Text(
                            item.subtitulo, 
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    
                    Spacer(Modifier.height(16.dp))

                    Text("Estado de lectura", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = when(item.status) {
                                BookStatus.POR_LEER -> "Por leer"
                                BookStatus.LEYENDO -> "Leyendo"
                                BookStatus.TERMINADO -> "Terminado"
                                else -> "Sin asignar"
                            },
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Sin asignar") },
                                onClick = {
                                    onStatusChange(BookStatus.NONE)
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Por leer") },
                                onClick = {
                                    onStatusChange(BookStatus.POR_LEER)
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Leyendo") },
                                onClick = {
                                    onStatusChange(BookStatus.LEYENDO)
                                    expanded = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Terminado") },
                                onClick = {
                                    onStatusChange(BookStatus.TERMINADO)
                                    expanded = false
                                }
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    
                    Text("Descripción", style = MaterialTheme.typography.titleLarge)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        itemDetalle.descripcion.ifBlank { "Sin descripción disponible." },
                        style = MaterialTheme.typography.bodyLarge
                    )

                    if (itemDetalle.atributos.isNotEmpty()) {
                        Spacer(Modifier.height(24.dp))
                        Text("Detalles", style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.height(8.dp))
                        itemDetalle.atributos.forEach { atributo ->
                            LabeledInfo(atributo.etiqueta, atributo.valor)
                        }
                    }

                    if (itemDetalle.relacionados.isNotEmpty()) {
                        Spacer(Modifier.height(24.dp))
                        Text("Relacionados", style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(itemDetalle.relacionados) { related ->
                                RelatedItemCard(related)
                            }
                        }
                    }
                    
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun RelatedItemCard(item: Item) {
    Card(
        modifier = Modifier.width(100.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            AsyncImage(
                model = item.imagenUrl,
                contentDescription = item.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(0.7f).background(Color.LightGray)
            )
            Text(
                item.titulo,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Suppress("FunctionName")
@Composable
private fun LabeledInfo(
    label: String,
    data: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(vertical = 4.dp)) {
        Spacer(Modifier.height(6.dp))
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("$label: ")
                }
                append(data)
            },
        )
    }
}
