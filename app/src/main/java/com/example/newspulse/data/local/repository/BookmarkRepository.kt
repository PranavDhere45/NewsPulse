package com.example.newspulse.data.local.repository

import com.example.newspulse.data.local.dao.BookmarkDao
import com.example.newspulse.data.local.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

class BookmarkRepository(
    private val bookmarkDao: BookmarkDao
) {

    suspend fun addBookmark(bookmark: BookmarkEntity) {
        bookmarkDao.insertBookmark(bookmark)
    }

    suspend fun removeBookmark(bookmark: BookmarkEntity) {
        bookmarkDao.deleteBookmark(bookmark)
    }

    fun getAllBookmarks(): Flow<List<BookmarkEntity>> {
        return bookmarkDao.getAllBookmarks()
    }

    suspend fun isBookmarked(title: String): Boolean {
        return bookmarkDao.isBookmarked(title)
    }
}
