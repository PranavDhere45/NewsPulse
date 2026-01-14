package com.example.newspulse.data.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class Article(
    val title: String,
    val description: String?,
    val image: String?,
    val content: String?,
    val publishedAt: String,
    val source: Source
) : Parcelable
