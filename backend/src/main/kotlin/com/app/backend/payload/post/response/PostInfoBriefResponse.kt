package com.app.backend.payload.post.response

import com.app.backend.model.post.EPostStatus
import com.app.backend.model.post.Post
import java.util.*

/**
 * @author Vladislav Nasevich
 */
data class PostInfoBriefResponse(
    val id: Long,
    val status: EPostStatus,
    val postTitle: String,
    val postLikeCount: Long,
    val creationDate: Date,
    val categories: List<String>
)

fun Post.toPostInfoBriefResponse() = PostInfoBriefResponse(
    id!!,
    postStatus,
    postTitle,
    postLikeCount,
    creationDate,
    categories.map { it.categoryName }
)
