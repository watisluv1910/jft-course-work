package com.app.backend.service

import com.app.backend.model.Blog
import com.app.backend.payload.blog.request.CreateBlogRequest
import com.app.backend.payload.blog.response.BlogInfoResponse
import com.app.backend.payload.blog.response.toBlogInfoResponse
import com.app.backend.repo.BlogRepository
import com.app.backend.repo.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Vladislav Nasevich
 */
@Service
class BlogService(
    private val blogRepository: BlogRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun createBlog(
        request: CreateBlogRequest,
        authorUsername: String
    ): BlogInfoResponse {
        val foundAuthor = userRepository.findOneByUsername(authorUsername)

        val savedBlog = blogRepository.save(
            Blog().apply {
                title = request.blogTitle
                description = request.blogDescription
                author = foundAuthor
                lastEditor = foundAuthor
            }
        )

        return savedBlog.toBlogInfoResponse()
    }
}
