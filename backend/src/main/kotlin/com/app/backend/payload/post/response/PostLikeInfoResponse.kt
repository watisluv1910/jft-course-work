package com.app.backend.payload.post.response

import com.app.backend.model.post.PostLike

/**
 * @author Vladislav Nasevich
 */
data class PostLikeInfoResponse(
    val postId: Long,
    val userId: Long
)

fun PostLike.toPostLikeInfoResponse() = PostLikeInfoResponse(
    post.id!!,
    user.id!!
)
