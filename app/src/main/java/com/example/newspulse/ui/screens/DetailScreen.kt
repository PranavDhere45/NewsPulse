package com.example.newspulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.newspulse.data.local.entity.BookmarkEntity
import com.example.newspulse.data.model.Article
import com.example.newspulse.ui.viewmodel.BookmarkViewModel
import com.example.newspulse.ui.viewmodel.BookmarkViewModelFactory

@Composable
fun DetailScreen(
    article: Article,
    bookmarkViewModel: BookmarkViewModel = viewModel(
        factory = BookmarkViewModelFactory(
            context = LocalContext.current
        )
    )
) {

    // Bookmark state
    var isBookmarked by remember { mutableStateOf(false) }

    // Check bookmark status when screen opens
    LaunchedEffect(article.title) {
        isBookmarked = bookmarkViewModel.isBookmarked(article.title)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            // Article Image
            article.image?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = article.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            // Title + Bookmark Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = {
                        isBookmarked = !isBookmarked

                        val bookmark = BookmarkEntity(
                            title = article.title,
                            description = article.description,
                            imageUrl = article.image,
                            content = article.content,
                            publishedAt = article.publishedAt,
                            sourceName = article.source.name
                        )

                        if (isBookmarked) {
                            bookmarkViewModel.addBookmark(bookmark)
                        } else {
                            bookmarkViewModel.removeBookmark(bookmark)
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isBookmarked)
                            Icons.Filled.Bookmark
                        else
                            Icons.Outlined.BookmarkBorder,
                        contentDescription = "Bookmark"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            article.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            // Content
            article.content?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
