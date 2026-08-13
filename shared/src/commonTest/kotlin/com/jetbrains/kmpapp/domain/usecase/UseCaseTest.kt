package com.jetbrains.kmpapp.domain.usecase

import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.ItemDetalle
import com.jetbrains.kmpapp.domain.repository.OpenLibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UseCaseTest {

    private class FakeRepository : OpenLibraryRepository {
        var searchResult: Result<List<Item>> = Result.success(emptyList())
        var detailResult: Result<ItemDetalle> = Result.failure(Exception())

        override fun search(query: String): Flow<Result<List<Item>>> = flowOf(searchResult)
        override fun getDetail(id: String): Flow<Result<ItemDetalle>> = flowOf(detailResult)
    }

    @Test
    fun `SearchItemsUseCase returns empty on blank query`() = runTest {
        val repo = FakeRepository()
        val useCase = SearchItemsUseCase(repo)
        val result = useCase("").first()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrThrow().isEmpty())
    }

    @Test
    fun `SearchItemsUseCase calls repository on valid query`() = runTest {
        val repo = FakeRepository()
        val expected = listOf(Item("1", "T", null, null, null, null, emptyList()))
        repo.searchResult = Result.success(expected)
        val useCase = SearchItemsUseCase(repo)
        
        val result = useCase("query").first()
        
        assertEquals(expected, result.getOrThrow())
    }

    @Test
    fun `GetItemDetailUseCase returns repository result`() = runTest {
        val repo = FakeRepository()
        val item = Item("1", "T", null, null, null, null, emptyList())
        val expected = ItemDetalle(item, "D", emptyList(), emptyList())
        repo.detailResult = Result.success(expected)
        val useCase = GetItemDetailUseCase(repo)

        val result = useCase("1").first()

        assertEquals(expected, result.getOrThrow())
    }
}
