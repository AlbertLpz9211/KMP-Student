package com.jetbrains.kmpapp.screens.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.screens.EmptyScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Suppress("FunctionName")
@Composable
fun ListScreen(navigateToDetails: (objectId: String) -> Unit) {
    val viewModel = koinViewModel<ListViewModel>()
    val itemList by viewModel.items.collectAsStateWithLifecycle()

    AnimatedContent(itemList.isNotEmpty()) { itemsAvailable ->
        if (itemsAvailable) {
            ObjectGrid(
                itemList = itemList,
                onItemClick = navigateToDetails,
            )
        } else {
            EmptyScreenContent(Modifier.fillMaxSize())
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
        columns = GridCells.Adaptive(180.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
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
    Column(
        modifier
            .padding(8.dp)
            .clickable { onClick() },
    ) {
        AsyncImage(
            model = item.imagenUrl,
            contentDescription = item.titulo,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.LightGray),
        )

        Spacer(Modifier.height(2.dp))

        Text(item.titulo, style = MaterialTheme.typography.titleMedium)
        item.subtitulo?.let {
            Text(it, style = MaterialTheme.typography.bodyMedium)
        }
        item.fecha?.let {
            Text(it, style = MaterialTheme.typography.bodySmall)
        }
    }
}
