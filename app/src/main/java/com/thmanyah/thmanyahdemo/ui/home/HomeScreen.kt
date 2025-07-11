package com.thmanyah.thmanyahdemo.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.thmanyahdemo.ui.common.HorizontalSquareList
import com.thmanyah.thmanyahdemo.ui.common.HorizontalTwoLinesGridList
import com.thmanyah.thmanyahdemo.ui.common.QueueHorizontalList
import com.thmanyah.thmanyahdemo.ui.common.ShowHorizontalBigSquareList
import com.thmanyah.thmanyahdemo.ui.common.WelcomeBar
import com.thmanyah.thmanyahdemo.ui.models.UiState
import com.thmanyah.thmanyahdemo.ui.models.home.HomeSectionUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val homeData by viewModel.homeData.collectAsStateWithLifecycle()
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        when (homeData) {
            is UiState.Init -> {
                // Initial state, could show a welcome message or empty state
                Text(
                    text = "Welcome to Thmanyah",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is UiState.Success, is UiState.LoadMore-> {
                val data = when (homeData) {
                    is UiState.Success -> (homeData as UiState.Success<HomeUiModel>).data
                    is UiState.LoadMore -> (homeData as UiState.LoadMore<HomeUiModel>).data
                    else -> return@Box
                }
                Column {
                    WelcomeBar(navController = navController )
                    HomeScreenList(data,isLoadingMore,viewModel)
                }
            }

            is UiState.Error -> {
                val error = (homeData as UiState.Error<HomeResponse>).error
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

        }
    }
}


@Composable
fun HomeScreenList(
    data: HomeUiModel,
    isLoadingMore: Boolean,
    viewModel: HomeViewModel
) {
    val listState = rememberLazyListState()

    // derivedStateOf to avoid recomputation unless scroll state actually changes
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItemsCount = listState.layoutInfo.totalItemsCount

            // Load more when the user scrolls near the bottom (2 items before the end)
            lastVisibleItemIndex != null &&
                    lastVisibleItemIndex >= totalItemsCount - 3 &&
                    !isLoadingMore
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            viewModel.loadNextPage()
        }
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
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
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

