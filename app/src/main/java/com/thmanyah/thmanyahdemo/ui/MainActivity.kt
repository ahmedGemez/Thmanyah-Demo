package com.thmanyah.thmanyahdemo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thmanyah.thmanyahdemo.ui.utils.NavigationKeys.Route.SEARCH
import com.thmanyah.thmanyahdemo.ui.home.HomeViewModel
import com.thmanyah.thmanyahdemo.ui.home.HomeScreen
import com.thmanyah.thmanyahdemo.ui.models.MainTab
import com.thmanyah.thmanyahdemo.ui.search.SearchScreen
import com.thmanyah.thmanyahdemo.ui.theme.ThmanyahDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThmanyahDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    MainScreen()

                }
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



@Composable
fun MainScreen() {
    val tabs = listOf(
        MainTab.Home,
        MainTab.Library,
        MainTab.Notifications,
        MainTab.Profile
    )
    val navController = rememberNavController()
    var selectedTab by remember { mutableStateOf(0) }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != SEARCH) {
                NavigationBar {
                    tabs.forEachIndexed { index, tab ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = {
                                selectedTab = index
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MainTab.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }

        ) {
            composable(MainTab.Home.route) { HomeScreen(navController = navController) }
            composable(MainTab.Library.route) { /* LibraryScreen() */ }
            composable(MainTab.Notifications.route) { /* NotificationsScreen() */ }
            composable(MainTab.Profile.route) { /* ProfileScreen() */ }
            composable(SEARCH) { SearchScreen() }
        }
    }
}

