package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.dto.CharacterResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class RickMortyApi(private val client: HttpClient) {

    suspend fun getCharacters(page: Int = 1): CharacterResponse {
        // GET https://rickandmortyapi.com/api/character?page=1
        return client.get("https://rickandmortyapi.com/api/character") {
            url {
                parameters.append("page", page.toString())
            }
        }.body()
    }
}