package com.jetbrains.kmpapp.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jetbrains.kmpapp.domain.model.Movie
import kotlin.math.roundToInt

/**
 * Póster de una película. Si no hay URL (o mientras carga), se ve un fondo con un emoji 🎬
 * como marcador de posición. Coil (AsyncImage) descarga y cachea la imagen por nosotros.
 */
@Composable
fun MoviePoster(
    url: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center,
    ) {
        // Marcador de posición debajo; si la imagen carga, la tapa.
        Text("🎬", style = MaterialTheme.typography.headlineMedium)
        if (url != null) {
            AsyncImage(
                model = url,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

/**
 * Tarjeta de una película: póster + título + rating/año. Reutilizada por la lista y por Favoritas.
 */
@Composable
fun MovieCard(pelicula: Movie, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier
            .padding(8.dp)
            .clickable(onClick = onClick),
    ) {
        MoviePoster(
            url = pelicula.posterUrl,
            contentDescription = pelicula.titulo,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
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

/** Estado: cargando (ruedita centrada). */
@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

/** Estado: error, con botón para reintentar. */
@Composable
fun ErrorState(
    mensaje: String,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(mensaje, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge)
        Button(onClick = onReintentar) { Text("Reintentar") }
    }
}

/** Estado: vacío (sin resultados). */
@Composable
fun EmptyState(mensaje: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Text(mensaje, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge)
    }
}
