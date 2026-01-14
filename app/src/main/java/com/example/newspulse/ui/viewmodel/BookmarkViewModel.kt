package com.example.newspulse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.local.entity.BookmarkEntity
import com.example.newspulse.data.local.repository.BookmarkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val repository: BookmarkRepository
) : ViewModel() {

    val bookmarks: StateFlow<List<BookmarkEntity>> =
        repository.getAllBookmarks()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun addBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            repository.addBookmark(bookmark)
        }
    }

    fun removeBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            repository.removeBookmark(bookmark)
        }
    }

    suspend fun isBookmarked(title: String): Boolean {
        return repository.isBookmarked(title)
    }
}
