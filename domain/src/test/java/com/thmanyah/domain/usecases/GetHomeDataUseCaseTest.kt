package com.thmanyah.domain.usecases

import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.models.Section
import com.thmanyah.domain.repositories.HomeRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetHomeDataUseCaseTest {

    private lateinit var homeRepo: HomeRepo
    private lateinit var getHomeDataUseCase: GetHomeDataUseCase

    @Before
    fun setup() {
        homeRepo = mockk()
        getHomeDataUseCase = GetHomeDataUseCase(homeRepo)
    }

    @Test
    fun `invoke should return sorted sections by order`() = runTest {
        // Given
        val unsortedResponse = HomeResponse(
            sections = listOf(
                Section(order = 3, name = "test3"),
                Section(order = 2, name = "test2"),
                Section(order = 1, name = "test1")
            )
        )
        val expectedSortedResponse = HomeResponse(
            sections = listOf(
                Section(order = 1, name = "test1"),
                Section(order = 2, name = "test2"),
                Section(order = 3, name = "test3"),
            )
        )
        coEvery { homeRepo.getHomeData() } returns flowOf(unsortedResponse)

        // When
        val result = getHomeDataUseCase().first()

        // Then
        assertEquals(expectedSortedResponse.sections, result.sections)
    }

    @Test
    fun `invoke should handle null sections`() = runTest {
        // Given
        val responseWithNullSections = HomeResponse(sections = null)
        coEvery { homeRepo.getHomeData() } returns flowOf(responseWithNullSections)

        // When
        val result = getHomeDataUseCase().first()

        // Then
        assertEquals(null, result.sections)
    }

    @Test
    fun `invoke should handle empty sections`() = runTest {
        // Given
        val responseWithEmptySections = HomeResponse(sections = emptyList())
        coEvery { homeRepo.getHomeData() } returns flowOf(responseWithEmptySections)

        // When
        val result = getHomeDataUseCase().first()

        // Then
        assertEquals(emptyList<Section>(), result.sections)
    }
}

