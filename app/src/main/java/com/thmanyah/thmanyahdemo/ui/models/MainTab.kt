package com.thmanyah.thmanyahdemo.ui.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MainTab(val route: String, val label: String, val icon: ImageVector) {
    object Home : MainTab("home", "الرئيسية", Icons.Filled.Home)
    object Search : MainTab("search", "بحث", Icons.Filled.Search)
    object Library : MainTab("library", "المكتبة", Icons.Filled.Menu)
    object Notifications : MainTab("notifications", "الإشعارات", Icons.Filled.Notifications)
    object Profile : MainTab("profile", "حسابي", Icons.Filled.Person)
}