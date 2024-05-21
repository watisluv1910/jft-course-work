package com.app.backend.payload.bookmark.request

data class CreateArticleBookmarkRequest(
    val articleUrl: String,
    val articleTitle: String
)
