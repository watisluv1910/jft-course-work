package com.app.backend.payload.post.response

import com.app.backend.model.post.Post

/**
 * @author Vladislav Nasevich
 */
data class PostLikesInfoResponse(
    val postId: Long,
    val likedUsersId: List<Long>
)

fun Post.toPostLikesInfoResponse() = PostLikesInfoResponse(
    id!!,
    likes.map { it.user.id!! }
)
