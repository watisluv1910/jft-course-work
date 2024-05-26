package com.app.backend.payload.blog.response

import com.app.backend.model.Blog
import com.app.backend.payload.user.response.UserInfoBriefResponse
import java.sql.Timestamp

/**
 * @author Vladislav Nasevich
 */
data class BlogInfoBriefResponse(
    val id: Long,
    val title: String,
    val description: String,
    val lastEditDate: Timestamp,
    val author: UserInfoBriefResponse,
    val lastEditor: UserInfoBriefResponse
)

fun Blog.toBlogInfoBriefResponse() = BlogInfoBriefResponse(
    id!!,
    title,
    description,
    lastEditDate!!,
    UserInfoBriefResponse.build(author),
    UserInfoBriefResponse.build(lastEditor)
)
