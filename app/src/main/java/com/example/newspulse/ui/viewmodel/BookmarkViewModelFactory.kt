package com.example.newspulse.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newspulse.data.local.database.AppDatabase
import com.example.newspulse.data.local.repository.BookmarkRepository

class BookmarkViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {

            val database = AppDatabase.getDatabase(context)
            val repository = BookmarkRepository(database.bookmarkDao())

            @Suppress("UNCHECKED_CAST")
            return BookmarkViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
