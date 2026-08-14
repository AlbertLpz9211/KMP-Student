package com.jetbrains.kmpapp.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.jetbrains.kmpapp.domain.model.BookStatus
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.screens.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("FunctionName")
@Composable
fun ListScreen(
    navigateToDetails: (objectId: String) -> Unit,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
    val viewModel = koinViewModel<ListViewModel>()
    val itemList by viewModel.items.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val myBooks by viewModel.myBooks.collectAsStateWithLifecycle()
    
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Open Library", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    label = { Text("Explorar") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                    label = { Text("Favoritos") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Bookmark, contentDescription = "My Books") },
                    label = { Text("Mis libros") }
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (selectedTab == 0) {
                var searchText by remember { mutableStateOf("Kotlin") }
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { 
                        searchText = it
                        viewModel.onQueryChange(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = { Text("Buscar libros...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    )
                )
            }

            var selectedStatus by remember { mutableStateOf(BookStatus.POR_LEER) }
            
            if (selectedTab == 2) {
                SecondaryScrollableTabRow(
                    selectedTabIndex = when(selectedStatus) {
                        BookStatus.POR_LEER -> 0
                        BookStatus.LEYENDO -> 1
                        BookStatus.TERMINADO -> 2
                        else -> 0
                    },
                    edgePadding = 16.dp,
                    containerColor = Color.Transparent,
                    divider = {}
                ) {
                    Tab(
                        selected = selectedStatus == BookStatus.POR_LEER,
                        onClick = { selectedStatus = BookStatus.POR_LEER },
                        text = { Text("Por leer") }
                    )
                    Tab(
                        selected = selectedStatus == BookStatus.LEYENDO,
                        onClick = { selectedStatus = BookStatus.LEYENDO },
                        text = { Text("Leyendo") }
                    )
                    Tab(
                        selected = selectedStatus == BookStatus.TERMINADO,
                        onClick = { selectedStatus = BookStatus.TERMINADO },
                        text = { Text("Terminados") }
                    )
                }
            }

            val displayList = when (selectedTab) {
                0 -> itemList
                1 -> favorites
                2 -> myBooks.filter { it.status == selectedStatus }
                else -> itemList
            }

            AnimatedContent(
                targetState = displayList,
                label = "GridAnimation"
            ) { list ->
                if (list.isNotEmpty()) {
                    ObjectGrid(
                        itemList = list,
                        onItemClick = navigateToDetails,
                    )
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            when (selectedTab) {
                                0 -> "No se encontraron resultados"
                                1 -> "Aún no tienes favoritos"
                                2 -> "No hay libros en '${when(selectedStatus) {
                                    BookStatus.POR_LEER -> "Por leer"
                                    BookStatus.LEYENDO -> "Leyendo"
                                    BookStatus.TERMINADO -> "Terminados"
                                    else -> ""
                                }}'"
                                else -> ""
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Suppress("FunctionName")
@Composable
private fun ObjectGrid(
    itemList: List<Item>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(110.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(itemList, key = { it.id }) { item ->
            ObjectFrame(
                item = item,
                onClick = { onItemClick(item.id) },
            )
        }
    }
}

@Suppress("FunctionName")
@Composable
private fun ObjectFrame(
    item: Item,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            Box {
                AsyncImage(
                    model = item.imagenUrl,
                    contentDescription = item.titulo,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.7f)
                        .background(Color.LightGray),
                )
                
                if (item.isFavorite) {
                    Surface(
                        shape = RoundedCornerShape(bottomStart = 8.dp),
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(16.dp)
                        )
                    }
                }
            }

            Column(Modifier.padding(6.dp)) {
                Text(
                    item.titulo, 
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                item.subtitulo?.let {
                    Text(
                        it, 
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
