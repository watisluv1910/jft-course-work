package com.app.backend.repo

import com.app.backend.model.post.Post
import com.app.backend.model.post.PostLike
import com.app.backend.model.user.User
import org.springframework.stereotype.Repository

/**
 * @author Vladislav Nasevich
 */
@Repository
interface PostLikeRepository: BaseRepository<PostLike> {

    fun deleteByPostAndUser(post: Post, user: User): Int
}
