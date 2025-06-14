package com.thmanyah.thmanyahdemo.ui.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainTab(val route: String, val label: String, val icon: ImageVector) {
    object Home : MainTab("home", "home", Icons.Filled.Home)
    object Library : MainTab("library", "library", Icons.Filled.Menu)
    object Notifications : MainTab("notifications", "notifications", Icons.Filled.Notifications)
    object Profile : MainTab("profile", "profile", Icons.Filled.Person)
}