package com.example.newspulse.ui.navigation

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")

    object Bookmarks : NavRoutes("bookmarks")
    object Detail : NavRoutes("detail")



}
