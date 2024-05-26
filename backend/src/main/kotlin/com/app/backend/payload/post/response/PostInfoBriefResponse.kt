package com.app.backend.payload.post.response

import com.app.backend.model.post.EPostStatus
import com.app.backend.model.post.Post
import com.app.backend.payload.user.response.UserInfoBriefResponse
import java.util.*

/**
 * @author Vladislav Nasevich
 */
data class PostInfoBriefResponse(
    val id: Long,
    val status: EPostStatus,
    val likedUsersId: List<Long>,
    val creationDate: Date,
    val author: UserInfoBriefResponse,
    val categories: List<String>,
    val title: String
)

fun Post.toPostInfoBriefResponse() = PostInfoBriefResponse(
    id!!,
    postStatus,
    likes.map { it.user.id!! },
    creationDate,
    UserInfoBriefResponse.build(blog.author),
    categories.map { it.categoryName },
    postTitle
)
