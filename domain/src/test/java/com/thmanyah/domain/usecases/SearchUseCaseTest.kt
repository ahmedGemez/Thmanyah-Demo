package com.thmanyah.domain.usecases

import com.thmanyah.domain.models.ContentType
import com.thmanyah.domain.models.SearchResponse
import com.thmanyah.domain.models.Section
import com.thmanyah.domain.models.SectionType
import com.thmanyah.domain.repositories.HomeRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import javax.naming.directory.SearchResult

class SearchUseCaseTest {

    private lateinit var homeRepo: HomeRepo
    private lateinit var searchUseCase: SearchUseCase

    @Before
    fun setup() {
        homeRepo = mockk()
        searchUseCase = SearchUseCase(homeRepo)
    }

    @Test
    fun `invoke should return search response from repository`() = runTest {
        // Given
        val expectedResponse = SearchResponse(
             listOf(
                 Section(
                    name = "1",
                     type = SectionType.QUEUE,
                     contentType = ContentType.EPISODE
                )
            )
        )
        coEvery { homeRepo.search() } returns flowOf(expectedResponse)

        // When
        val result = searchUseCase().first()

        // Then
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `invoke should return empty search response when repository returns empty results`() = runTest {
        // Given
        val expectedResponse = SearchResponse(emptyList())
        coEvery { homeRepo.search() } returns flowOf(expectedResponse)

        // When
        val result = searchUseCase().first()

        // Then
        assertEquals(expectedResponse, result)
        assertEquals(0, result.sections?.size)
    }
}

