package com.thmanyah.thmanyahdemo.ui.search


import com.thmanyah.domain.models.ContentType
import com.thmanyah.domain.models.SearchResponse
import com.thmanyah.domain.models.Section
import com.thmanyah.domain.models.SectionType
import com.thmanyah.domain.usecases.SearchUseCase
import com.thmanyah.thmanyahdemo.R
import com.thmanyah.thmanyahdemo.ui.models.UiState
import com.thmanyah.thmanyahdemo.ui.models.home.HomeSectionUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var searchUseCase: SearchUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        searchUseCase = mockk()
        viewModel = SearchViewModel(searchUseCase, testDispatcher)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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
            content = emptyList()
        )
    }


    @Test
    fun `should emit success state with search results`() = runTest {
        // Given
        val searchResponse = SearchResponse(
            sections = listOf(
                createTestSection(
                    name = "Search Results",
                    type = SectionType.SQUARE,
                    contentType = ContentType.PODCAST
                )
            )
        )

        coEvery { searchUseCase() } returns flow {
            emit(searchResponse)
        }

        // When
        viewModel = SearchViewModel(searchUseCase, testDispatcher)
        viewModel.getSearchData("test query")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.searchData.value is UiState.Success)
        val successState = viewModel.searchData.value as UiState.Success<HomeUiModel>
        assertEquals(1, successState.data.sections.size)
        assertEquals("Search Results", (successState.data.sections[0] as HomeSectionUiModel.Square).name)
    }

    @Test
    fun `should emit error state when search fails`() = runTest {
        // Given
        val error = UnknownHostException("Search failed")
        coEvery { searchUseCase() } returns flow {
            throw error
        }

        // When
        viewModel = SearchViewModel(searchUseCase, testDispatcher)
        viewModel.getSearchData("test query")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.searchData.value is UiState.Error)
        val errorState = viewModel.searchData.value as UiState.Error
        assertEquals(R.string.no_internet_connection, errorState.error.messageRes)
    }

    @Test
    fun `should emit empty state when search returns no results`() = runTest {
        // Given
        val emptyResponse = SearchResponse(
            sections = emptyList()
        )

        coEvery { searchUseCase() } returns flow {
            emit(emptyResponse)
        }

        // When
        viewModel = SearchViewModel(searchUseCase, testDispatcher)
        viewModel.getSearchData("test query")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.searchData.value is UiState.Success)
        val successState = viewModel.searchData.value as UiState.Success<HomeUiModel>
        assertTrue(successState.data.sections.isEmpty())
    }
}