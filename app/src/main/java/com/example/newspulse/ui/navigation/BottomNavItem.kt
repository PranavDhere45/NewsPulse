package com.example.newspulse.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Home : BottomNavItem(
        route = NavRoutes.Home.route,
        title = "Home",
        icon = Icons.Filled.Home
    )

    object Bookmarks : BottomNavItem(
        route = NavRoutes.Bookmarks.route,
        title = "Bookmarks",
        icon = Icons.Filled.Bookmark
    )
}
