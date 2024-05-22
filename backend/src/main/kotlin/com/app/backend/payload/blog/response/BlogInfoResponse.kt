package com.app.backend.payload.blog.response

import com.app.backend.model.Blog
import com.app.backend.payload.post.response.PostInfoBriefResponse
import com.app.backend.payload.post.response.toPostInfoBriefResponse
import com.app.backend.payload.user.response.UserInfoResponse
import java.sql.Timestamp

/**
 * @author Vladislav Nasevich
 */
data class BlogInfoResponse(
    val id: Long,
    val title: String,
    val description: String,
    val lastEditDate: Timestamp,
    val author: UserInfoResponse,
    val lastEditor: UserInfoResponse,
    val posts: List<PostInfoBriefResponse>
)

fun Blog.toBlogInfoResponse() = BlogInfoResponse(
    id!!,
    title,
    description,
    lastEditDate!!,
    UserInfoResponse.build(author),
    UserInfoResponse.build(lastEditor),
    posts.map { it.toPostInfoBriefResponse() }
)
