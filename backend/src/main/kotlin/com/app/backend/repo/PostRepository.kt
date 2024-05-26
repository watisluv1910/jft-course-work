package com.app.backend.repo

import com.app.backend.model.Blog
import com.app.backend.model.post.Post
import com.app.backend.model.post.PostCategory
import org.springframework.stereotype.Repository

/**
 * @author Vladislav Nasevich
 */
@Repository
interface PostRepository: BaseRepository<Post> {

    fun findAllByBlog(blog: Blog): MutableList<Post>

    fun findAllByPostTitleIsContainingIgnoreCase(postTitle: String): MutableList<Post>

    fun findAllByCategoriesIsContaining(categories: List<PostCategory>): MutableList<Post>
}
