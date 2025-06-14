package com.thmanyah.thmanyahdemo.ui.home.homeui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.thmanyahdemo.ui.home.HomeViewModel
import com.thmanyah.thmanyahdemo.ui.models.UiState
import com.thmanyah.thmanyahdemo.ui.models.home.HomeSectionUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val homeData by viewModel.homeData.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()

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
                    LazyColumn {
                        itemsIndexed(data.sections) { index, section ->
                            if (index == data.sections.lastIndex - 2 && !isLoadingMore) {
                                viewModel.loadNextPage()
                            }
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
                        if (isLoadingMore) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
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
