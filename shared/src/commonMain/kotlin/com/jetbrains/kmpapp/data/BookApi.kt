package com.jetbrains.kmpapp.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class BookApi(private val client: HttpClient) {
    suspend fun getBooks(query: String = "kotlin"): BookResponse {
        return client.get("https://openlibrary.org/search.json") {
            parameter("q", query)
            parameter("limit", 20)
        }.body()
    }

    suspend fun getBookDetails(id: String): BookDocDto {
        // Open Library details is a bit more complex, for now we just reuse the search result
        // or fetch by key. Let's simplify and just use the search result in the repo.
        return client.get("https://openlibrary.org$id.json").body()
    }
}
