package com.jetbrains.kmpapp.data.mapper

import com.jetbrains.kmpapp.data.remote.ItemDTO
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.ItemDetalle

class ApiMapper {
    fun toDomain(dto: ItemDTO): Item {
        return Item(
            id = (dto.trackId ?: dto.collectionId ?: 0).toString(),
            titulo = dto.trackName ?: dto.collectionName ?: "Sin título",
            subtitulo = dto.artistName,
            imagenUrl = dto.artworkUrl100,
            metrica = dto.trackPrice ?: dto.collectionPrice,
            fecha = dto.releaseDate,
            tags = listOfNotNull(dto.primaryGenreName, dto.kind, dto.wrapperType)
        )
    }

    fun toDetailDomain(dto: ItemDTO): ItemDetalle {
        return ItemDetalle(
            id = (dto.trackId ?: dto.collectionId ?: 0).toString(),
            titulo = dto.trackName ?: dto.collectionName ?: "Sin título",
            subtitulo = dto.artistName,
            imagenUrl = dto.artworkUrl100,
            metrica = dto.trackPrice ?: dto.collectionPrice,
            fecha = dto.releaseDate,
            tags = listOfNotNull(dto.primaryGenreName, dto.kind, dto.wrapperType),
            descripcion = dto.longDescription ?: dto.description,
            precio = (dto.trackPrice ?: dto.collectionPrice)?.toString(),
            moneda = dto.currency,
            pais = dto.country,
            urlPreview = dto.previewUrl
        )
    }
}
