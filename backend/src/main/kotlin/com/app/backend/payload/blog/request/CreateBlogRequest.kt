package com.app.backend.payload.blog.request

/**
 * @author Vladislav Nasevich
 */
data class CreateBlogRequest(
    val blogTitle: String,
    val blogDescription: String
)
