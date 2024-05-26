package com.app.backend.service

import com.app.backend.model.Blog
import com.app.backend.model.user.User
import com.app.backend.model.user.role.EUserRole
import com.app.backend.payload.blog.request.UpsertBlogRequest
import com.app.backend.payload.blog.response.BlogInfoBriefResponse
import com.app.backend.payload.blog.response.BlogInfoResponse
import com.app.backend.payload.blog.response.toBlogInfoBriefResponse
import com.app.backend.payload.blog.response.toBlogInfoResponse
import com.app.backend.repo.BlogRepository
import com.app.backend.repo.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.*

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
        request: UpsertBlogRequest,
        authorUsername: String
    ): BlogInfoBriefResponse {
        val foundAuthor = userRepository.findOneByUsername(authorUsername)

        val savedBlog = blogRepository.save(
            Blog().apply {
                title = request.title.lowercase().capitalizeWords()
                description = request.description
                author = foundAuthor
                lastEditor = foundAuthor
            }
        )

        return savedBlog.toBlogInfoBriefResponse()
    }

    fun findAllByTitle(query: String): List<BlogInfoBriefResponse> {
        val title = query.replace("\\++".toRegex(), " ").trim()

        return blogRepository
            .findAllByTitleIsContainingIgnoreCase(title)
            .sortedByDescending { it.creationDate }
            .map { it.toBlogInfoBriefResponse() }
    }

    fun findAll(): List<BlogInfoBriefResponse> {
        return blogRepository
            .findAll()
            .sortedByDescending { it.creationDate }
            .map { it.toBlogInfoBriefResponse() }
    }

    fun findById(id: Long): BlogInfoResponse {
        return blogRepository.findOneById(id).toBlogInfoResponse()
    }

    @Transactional
    fun updateBlogInfo(
        blogId: Long,
        request: UpsertBlogRequest,
        editorUsername: String
    ): BlogInfoResponse {
        val (foundUser, foundBlog) = hasRightsToModify(editorUsername, blogId)

        val updatedBlog = foundBlog.apply {
            title = request.title.capitalizeWords()
            description = request.description
            lastEditor = foundUser
            lastEditDate = Timestamp(Date().time)
        }

        return updatedBlog.toBlogInfoResponse()
    }

    @Transactional
    fun deleteBlog(blogId: Long, editorUsername: String) {
        val (_, foundBlog) = hasRightsToModify(editorUsername, blogId)

        blogRepository.delete(foundBlog)
    }

    fun hasRightsToModify(
        editorUsername: String,
        blogId: Long
    ): Pair<User, Blog> {
        val foundUser = userRepository.findOneByUsername(editorUsername)

        val foundBlog = blogRepository.findByIdOrNull(blogId)?.also {
            if (foundUser.canNotModify(it)) {
                throw SecurityException(
                    "User with username: ${foundUser.username} doesn't have " +
                            "the authority to modify blog with id: ${it.id}"
                )
            }
        } ?: throw NoSuchElementException(
            "Blog with id: $blogId not found. " +
                    "It may be deleted or not created yet."
        )

        return Pair(foundUser, foundBlog)
    }

}

private fun User.canNotModify(blog: Blog) =
    this.username != blog.author.username &&
            !this.roles.map { r -> r.roleName }
                .contains(EUserRole.ROLE_MODERATOR)

fun String.capitalizeWords(): String =
    split(" ")
        .map { w -> w.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) } }
        .joinToString(" ")
