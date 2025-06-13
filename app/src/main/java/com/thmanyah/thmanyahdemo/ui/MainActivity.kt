package com.thmanyah.thmanyahdemo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.thmanyahdemo.ui.models.UiState
import com.thmanyah.thmanyahdemo.ui.theme.ThmanyahDemoTheme
import com.thmanyah.thmanyahdemo.ui.utils.toUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThmanyahDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
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
                val data = (homeData as UiState.Success<HomeResponse>).data
                // Display your content here
                Text(
                    text = "Found ${data.sections?.size ?: 0} sections",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is UiState.Error -> {
                val error = (homeData as UiState.Error<HomeResponse>).error
                Text(
                    text = error.messageRes?.let { stringResource(id = it) } ?: error.message ?: "An error occurred",
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ThmanyahDemoTheme {
        // Preview placeholder
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Preview",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}