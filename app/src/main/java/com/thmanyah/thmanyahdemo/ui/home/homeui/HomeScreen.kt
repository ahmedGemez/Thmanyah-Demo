package com.thmanyah.thmanyahdemo.ui.home.homeui

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
import com.thmanyah.thmanyahdemo.ui.models.home.HomeSectionUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel
import com.thmanyah.thmanyahdemo.ui.models.UiState
import java.util.UUID

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val homeData by viewModel.homeData.collectAsState()

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

            is UiState.Success -> {
                val data = (homeData as UiState.Success<HomeUiModel>).data
                Column {
                    WelcomeBar(navController = navController )
                    LazyColumn {
                        items(data.sections, key = { UUID.randomUUID() }) { section ->
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

            is UiState.LoadMore -> {
                // Handle load more state if needed
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
