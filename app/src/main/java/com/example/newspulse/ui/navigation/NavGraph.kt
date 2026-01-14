package com.example.newspulse.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.example.newspulse.ui.screens.BookmarkScreen
import com.example.newspulse.ui.screens.HomeScreen
import com.example.newspulse.ui.screens.DetailScreen
import com.example.newspulse.data.model.Article

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(navController: NavHostController) {

    AnimatedNavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {

        // 🏠 HOME
        composable(
            route = NavRoutes.Home.route,
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300))
            }
        ) {
            HomeScreen(navController = navController)
        }

        // 📰 DETAIL
        composable(
            route = NavRoutes.Detail.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            val article =
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<Article>("article")

            article?.let {
                DetailScreen(article = it)
            }
        }

        // 🔖 BOOKMARKS
        composable(
            route = NavRoutes.Bookmarks.route,
            enterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            BookmarkScreen()
        }
    }
}
