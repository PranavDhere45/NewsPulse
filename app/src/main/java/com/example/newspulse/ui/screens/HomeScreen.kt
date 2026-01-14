package com.example.newspulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.newspulse.ui.components.NewsItemPlaceholder
import com.example.newspulse.ui.navigation.NavRoutes
import com.example.newspulse.ui.viewmodel.NewsViewModel
import com.example.newspulse.ui.components.ErrorState
import com.example.newspulse.ui.components.EmptyState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.Crossfade




@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    newsViewModel: NewsViewModel = viewModel()
) {

    val articles by newsViewModel.articles.collectAsState()
    val isLoading by newsViewModel.isLoading.collectAsState()
    val error by newsViewModel.error.collectAsState()


    var searchQuery by remember { mutableStateOf("") }

    val categories = listOf(
        "Technology",
        "Business",
        "Sports",
        "Health",
        "Entertainment"
    )

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { newsViewModel.loadNews() }
    )

    LaunchedEffect(Unit) {
        newsViewModel.loadNews()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("NewsPulse") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )

        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                // 🔍 Search bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    placeholder = { Text("Search news...") },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (searchQuery.isNotBlank()) {
                                    newsViewModel.searchNews(searchQuery)
                                }
                            }
                        ) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                    }
                )

                // 🗂 Categories
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    items(categories) { category ->
                        AssistChip(
                            onClick = { newsViewModel.loadCategory(category) },
                            label = { Text(category) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            ),
                            elevation = AssistChipDefaults.assistChipElevation(
                                pressedElevation = 2.dp
                            )
                        )

                    }
                }

                Spacer(modifier = Modifier.height(8.dp))


                // 📰 CONTENT STATES
                Crossfade(
                    targetState = Triple(error, isLoading, articles.isEmpty()),
                    label = "home-content"
                ) { (err, loading, empty) ->

                    when {

                        // 🔴 ERROR STATE (highest priority)
                        err != null -> {
                            ErrorState(
                                message = err ?: "Something went wrong",
                                onRetry = { newsViewModel.loadNews() }
                            )
                        }

                        // ⏳ LOADING STATE (Phase 7.1 placeholder)
                        loading && empty -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(
                                    start = 8.dp,
                                    end = 8.dp,
                                    top = 8.dp,
                                    bottom = 80.dp
                                )
                            ) {
                                items(5) {
                                    NewsItemPlaceholder()
                                }
                            }
                        }

                        // 📭 EMPTY STATE (after loading)
                        empty -> {
                            EmptyState(message = "No news available")
                        }

                        // ✅ SUCCESS STATE (Phase 7.4.1 animation)
                        else -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(
                                    start = 8.dp,
                                    end = 8.dp,
                                    top = 8.dp,
                                    bottom = 80.dp
                                )
                            ) {
                                items(
                                    items = articles
                                    // IMPORTANT: stable animations
                                ) { article ->

                                    var visible by remember { mutableStateOf(false) }
                                    LaunchedEffect(Unit) {
                                        visible = true
                                    }


                                    AnimatedVisibility(
                                        visible = visible,
                                        enter = fadeIn() + slideInVertically { it / 4 }
                                    ) {
                                        NewsItem(
                                            article = article,
                                            onClick = {
                                                navController.currentBackStackEntry
                                                    ?.savedStateHandle
                                                    ?.set("article", article)

                                                navController.navigate(NavRoutes.Detail.route)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 🔄 Pull-to-refresh indicator
            PullRefreshIndicator(
                refreshing = isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
