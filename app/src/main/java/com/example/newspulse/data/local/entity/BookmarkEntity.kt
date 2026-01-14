package com.example.newspulse.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val description: String?,
    val imageUrl: String?,
    val content: String?,
    val publishedAt: String,
    val sourceName: String
)
