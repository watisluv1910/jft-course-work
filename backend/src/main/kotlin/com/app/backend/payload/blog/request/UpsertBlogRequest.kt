package com.app.backend.payload.blog.request

/**
 * @author Vladislav Nasevich
 */
data class UpsertBlogRequest(
    val title: String,
    val description: String
)
