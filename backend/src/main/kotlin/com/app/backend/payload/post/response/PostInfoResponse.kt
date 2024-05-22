package com.app.backend.payload.post.response

import com.app.backend.model.post.EPostStatus
import com.app.backend.model.post.Post
import com.app.backend.payload.user.response.UserInfoResponse
import java.util.*

/**
 * @author Vladislav Nasevich
 */
data class PostInfoResponse(
    val id: Long,
    val status: EPostStatus,
    val likedUsersId: List<Long>,
    val creationDate: Date,
    val author: UserInfoResponse,
    val lastEditDate: Date,
    val lastEditor: UserInfoResponse,
    val categories: List<String>,
    val title: String,
    val description: String,
    val content: String
)

fun Post.toPostInfoResponse() = PostInfoResponse(
    id!!,
    postStatus,
    likes.map { it.user.id!! },
    creationDate,
    UserInfoResponse.build(blog.author),
    postMeta.lastEditDate,
    UserInfoResponse.build(postMeta.lastEditor),
    categories.map { it.categoryName },
    postTitle,
    postDescription,
    postMeta.postBody
)
