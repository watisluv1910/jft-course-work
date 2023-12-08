package com.app.backend.payload.bookmark.request

data class CreateBookmarkRequest(
    val articleUrl: String,
    val articleTitle: String
)
