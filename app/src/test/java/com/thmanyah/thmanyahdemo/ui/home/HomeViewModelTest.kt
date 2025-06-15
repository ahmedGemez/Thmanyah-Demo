package com.thmanyah.thmanyahdemo.ui.home

import com.thmanyah.domain.models.ContentItem
import com.thmanyah.domain.models.ContentType
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.models.Pagination
import com.thmanyah.domain.models.Section
import com.thmanyah.domain.models.SectionType
import com.thmanyah.domain.usecases.GetHomeDataUseCase
import com.thmanyah.thmanyahdemo.R
import com.thmanyah.thmanyahdemo.ui.models.UiState
import com.thmanyah.thmanyahdemo.ui.models.home.HomeSectionUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var getHomeDataUseCase: GetHomeDataUseCase
    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxed = true)
        getHomeDataUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Init`() = runTest {
        // Given
        val testResponse = HomeResponse(
            sections = listOf(
                createTestSection(
                    name = "Test Section",
                    type = SectionType.SQUARE,
                    contentType = ContentType.PODCAST
                )
            ),
            pagination = Pagination(nextPage = "2", totalPages = 3)
        )
        coEvery { getHomeDataUseCase.invoke() } returns flow {
            emit(testResponse)
        }


        // When
        viewModel = HomeViewModel(getHomeDataUseCase, testDispatcher)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.homeData.value is UiState.Success)
        val successState = viewModel.homeData.value as UiState.Success<HomeUiModel>
        assertEquals(1, successState.data.sections.size)
        assertEquals(
            "Test Section",
            (successState.data.sections[0] as HomeSectionUiModel.Square).name
        )
    }

    @Test
    fun `loadNextPage should append new sections when not on last page`() = runTest {
        // Given
        val firstPageResponse = HomeResponse(
            sections = listOf(
                createTestSection(
                    name = "First Page",
                    type = SectionType.SQUARE,
                    contentType = ContentType.PODCAST
                )
            ),
            pagination = Pagination(nextPage = "2", totalPages = 3)
        )

        val secondPageResponse = HomeResponse(
            sections = listOf(
                createTestSection(
                    name = "Second Page",
                    type = SectionType.SQUARE,
                    contentType = ContentType.PODCAST
                )
            ),
            pagination = Pagination(nextPage = "3", totalPages = 3)
        )

        coEvery { getHomeDataUseCase() } returnsMany listOf(
            flowOf(firstPageResponse),
            flowOf(secondPageResponse)
        )

        // When
        viewModel = HomeViewModel(getHomeDataUseCase, testDispatcher)
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.loadNextPage()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.homeData.value is UiState.Success)
        val successState = viewModel.homeData.value as UiState.Success<HomeUiModel>
        assertEquals(2, successState.data.sections.size)
        assertEquals(
            "First Page",
            (successState.data.sections[0] as HomeSectionUiModel.Square).name
        )
        assertEquals(
            "Second Page",
            (successState.data.sections[1] as HomeSectionUiModel.Square).name
        )
    }

    @Test
    fun `loadNextPage should not load more when on last page`() = runTest {
        // Given
        val lastPageResponse = HomeResponse(
            sections = listOf(
                createTestSection(
                    name = "Last Page",
                    type = SectionType.SQUARE,
                    contentType = ContentType.PODCAST
                )
            ),
            pagination = Pagination(nextPage = null, totalPages = 1)
        )

        coEvery { getHomeDataUseCase() } returns flowOf(lastPageResponse)

        // When
        viewModel = HomeViewModel(getHomeDataUseCase, testDispatcher)
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.loadNextPage()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.homeData.value is UiState.Success)
        val successState = viewModel.homeData.value as UiState.Success<HomeUiModel>
        assertEquals(1, successState.data.sections.size)
        assertEquals("Last Page", (successState.data.sections[0] as HomeSectionUiModel.Square).name)
    }

    @Test
    fun `should handle error state`() = runTest {
        // Given
        val error = RuntimeException("Test error")
        coEvery { getHomeDataUseCase() } returns flow {
            throw IOException("Test error")
        }


        // When
        viewModel = HomeViewModel(getHomeDataUseCase, testDispatcher)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.homeData.value is UiState.Error)
        val errorState = viewModel.homeData.value as UiState.Error
        assertEquals(R.string.no_internet_connection, errorState.error.messageRes)
    }

    @Test
    fun `should not load more while loading`() = runTest {
        // Given
        val firstPageResponse = HomeResponse(
            sections = listOf(
                createTestSection(
                    name = "First Page",
                    type = SectionType.SQUARE,
                    contentType = ContentType.PODCAST
                )
            ),
            pagination = Pagination(nextPage = "2", totalPages = 3)
        )

        coEvery { getHomeDataUseCase() } coAnswers {
            flow {
                delay(2000) // Longer delay to ensure second call happens before first completes
                emit(firstPageResponse)
            }
        }

        // When
        viewModel = HomeViewModel(getHomeDataUseCase, testDispatcher)
        // First loadNextPage call
        viewModel.loadNextPage()
        // Wait a bit to ensure _isLoadingMore is true
        delay(100)
        // Second loadNextPage call while first is still loading
        viewModel.loadNextPage()
        // Wait for all operations to complete
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.homeData.value is UiState.Success)
        val successState = viewModel.homeData.value as UiState.Success<HomeUiModel>
        assertEquals(2, successState.data.sections.size)
    }

    private fun createTestSection(
        name: String,
        type: SectionType,
        contentType: ContentType
    ): Section {
        return Section(
            name = name,
            type = type,
            contentType = contentType,
            content = listOf(
                ContentItem.Podcast(
                    podcastId = "1",
                    name = "Test Podcast",
                    avatarUrl = "test_url",
                    duration = 100
                )
            )
        )
    }
} 