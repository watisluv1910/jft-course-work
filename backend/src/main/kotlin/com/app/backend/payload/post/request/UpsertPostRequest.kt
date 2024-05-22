package com.app.backend.payload.post.request

/**
 * @author Vladislav Nasevich
 */
data class UpsertPostRequest(
    val title: String,
    val description: String,
    val content: String,
    val categories: List<String>
)
