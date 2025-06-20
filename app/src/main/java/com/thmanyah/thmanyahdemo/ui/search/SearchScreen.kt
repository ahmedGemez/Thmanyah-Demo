package com.thmanyah.thmanyahdemo.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.thmanyahdemo.ui.common.HorizontalSquareList
import com.thmanyah.thmanyahdemo.ui.common.HorizontalTwoLinesGridList
import com.thmanyah.thmanyahdemo.ui.common.QueueHorizontalList
import com.thmanyah.thmanyahdemo.ui.common.SearchInput
import com.thmanyah.thmanyahdemo.ui.common.ShowHorizontalBigSquareList
import com.thmanyah.thmanyahdemo.ui.models.UiState
import com.thmanyah.thmanyahdemo.ui.models.home.HomeSectionUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {

    val viewModel: SearchViewModel = hiltViewModel()
    val searchData by viewModel.searchData.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }
    var previousQuery by remember { mutableStateOf("") }


    // Debounce query changes
    LaunchedEffect(query) {
        snapshotFlow { query.trim() }
            .distinctUntilChanged()
            .debounce(200)
            .collectLatest { debouncedQuery ->
                // Use previousQuery to prevent redundant API calls with the same search input
                if (debouncedQuery.isNotBlank() && debouncedQuery != previousQuery) {
                    previousQuery = debouncedQuery
                    viewModel.getSearchData(debouncedQuery)
                }
            }
    }

    Column {
        SearchInput(
            query = query,
            onQueryChange = { query = it }
        )

        Box(modifier = modifier.fillMaxSize()) {
            when (searchData) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is UiState.Success -> {
                    val data = (searchData as UiState.Success<HomeUiModel>).data
                    Column {
                        LazyColumn {
                            items(data.sections) { section ->
                                when (section) {
                                    is HomeSectionUiModel.Square -> {
                                        HorizontalSquareList(section.items, section.name)
                                    }

                                    is HomeSectionUiModel.TwoLinesGrid -> {
                                        HorizontalTwoLinesGridList(section.items, section.name)
                                    }

                                    is HomeSectionUiModel.BigSquare -> {
                                        ShowHorizontalBigSquareList(section.items, section.name)
                                    }

                                    is HomeSectionUiModel.Queue -> {
                                        QueueHorizontalList(section.items, section.name)
                                    }
                                    // Add more cases as needed
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    val error = (searchData as UiState.Error<HomeResponse>).error
                    Text(
                        text = error.messageRes?.let { stringResource(id = it) } ?: error.message
                        ?: "An error occurred",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is UiState.Empty -> {
                    Text(
                        text = "No data available",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> Unit
            }
        }

    }
}

