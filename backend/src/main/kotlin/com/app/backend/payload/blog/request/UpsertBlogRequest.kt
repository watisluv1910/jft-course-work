package com.app.backend.payload.blog.request

/**
 * @author Vladislav Nasevich
 */
data class UpsertBlogRequest(
    val blogTitle: String,
    val blogDescription: String
)
