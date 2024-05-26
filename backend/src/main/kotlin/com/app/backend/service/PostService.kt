package com.app.backend.service

import com.app.backend.model.post.Post
import com.app.backend.model.post.PostCategory
import com.app.backend.model.post.PostLike
import com.app.backend.model.post.PostMeta
import com.app.backend.model.user.User
import com.app.backend.payload.post.request.UpsertPostRequest
import com.app.backend.payload.post.response.*
import com.app.backend.repo.*
import jakarta.persistence.EntityExistsException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.sql.Timestamp
import java.util.*

@Service
class PostService(
    private val postRepository: PostRepository,
    private val postCategoryRepository: PostCategoryRepository,
    private val postLikeRepository: PostLikeRepository,
    private val userRepository: UserRepository,
    private val blogService: BlogService,
    private val blogRepository: BlogRepository
) {

    @Transactional
    fun createPost(
        request: UpsertPostRequest,
        blogId: Long,
        authorUsername: String
    ): PostInfoBriefResponse {
        val (foundUser, foundBlog) = blogService.hasRightsToModify(
            authorUsername,
            blogId
        )

        val savedCategories = getSavedCategories(request.categories)

        val post = Post().apply {
            postTitle = request.title.capitalizeWords()
            postDescription = request.description
            blog = foundBlog
            categories = savedCategories.toMutableSet()
        }

        val postMeta = PostMeta().apply {
            postBody = request.content
            lastEditor = foundUser
            this.post = post
        }

        post.postMeta = postMeta

        val savedPost = postRepository.save(post)

        return savedPost.toPostInfoBriefResponse()
    }

    fun findById(blogId: Long, postId: Long): PostInfoResponse {
        return blogRepository.findOneById(blogId).posts.find { it.id == postId }
            ?.toPostInfoResponse()
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Post with id: $postId in blog with id: $blogId not found. " +
                        "It may be deleted, not yet be created or belong to another blog."
            )
    }

    @Transactional
    fun updatePostInfo(
        blogId: Long,
        postId: Long,
        request: UpsertPostRequest,
        editorUsername: String
    ): PostInfoResponse {
        val (foundUser, foundBlog) = blogService.hasRightsToModify(
            editorUsername,
            blogId
        )

        val updatedPost = foundBlog.posts.find { it.id == postId }?.apply {
            postTitle = request.title.capitalizeWords()
            postDescription = request.description
            categories = getSavedCategories(request.categories).toMutableSet()
            postMeta.postBody = request.content
            postMeta.lastEditor = foundUser
            postMeta.lastEditDate = Timestamp(Date().time)
        } ?: throw NoSuchElementException(
            "Post with id: $postId in blog with id: $blogId not found. " +
                    "It may be deleted, not yet be created or belong to another blog."
        )

        return updatedPost.toPostInfoResponse()
    }

    @Transactional
    fun deletePost(blogId: Long, postId: Long, username: String) {
        val (_, foundBlog) = blogService.hasRightsToModify(username, blogId)

        if (foundBlog.posts.none { it.id == postId })
            throw NoSuchElementException(
                "Post with id: $postId in blog with id: $blogId not found. " +
                        "It may be deleted, not yet be created or belong to another blog."
            )

        postRepository.deleteById(postId)
    }

    private fun getSavedCategories(categoriesNames: List<String>) =
        categoriesNames.map {
            postCategoryRepository.findFirstByCategoryNameIgnoreCase(it)
                ?: postCategoryRepository.save(PostCategory().apply {
                    categoryName = it.lowercase()
                })
        }

    @Transactional
    fun likePost(
        blogId: Long,
        postId: Long,
        username: String
    ): PostLikesInfoResponse {
        val foundUser = userRepository.findOneByUsername(username)
        val foundPost =
            blogRepository.findOneById(blogId).posts.find { it.id == postId }
                ?: throw NoSuchElementException(
                    "Post with id: $postId in blog with id: $blogId not found. " +
                            "It may be deleted, not yet be created or belong to another blog."
                )

        if (isLikedBy(foundPost, foundUser))
            throw EntityExistsException(
                "Can't add like to post with " +
                        "id: $postId in blog with " +
                        "id: $blogId. Post is already liked by user $username"
            )

        val savedLike = postLikeRepository.save(
            PostLike().apply {
                user = foundUser
                post = foundPost
            }
        )

        foundPost.likes.add(savedLike)

        return foundPost.toPostLikesInfoResponse()
    }

    @Transactional
    fun dislikePost(
        blogId: Long,
        postId: Long,
        username: String
    ): PostLikesInfoResponse {
        val foundUser = userRepository.findOneByUsername(username)
        val foundPost =
            blogRepository.findOneById(blogId).posts.find { it.id == postId }
                ?: throw NoSuchElementException(
                    "Post with id: $postId in blog with id: $blogId not found. " +
                            "It may be deleted, not yet be created or belong to another blog."
                )

        if (!isLikedBy(foundPost, foundUser))
            throw NoSuchElementException(
                "Can't remove like from post with " +
                        "id: $postId in blog with " +
                        "id: $blogId. Post is not liked by user $username"
            )

        postLikeRepository.deleteByPostAndUser(foundPost, foundUser)

        foundPost.likes.retainAll { it.user != foundUser }

        return foundPost.toPostLikesInfoResponse()
    }

    private fun isLikedBy(
        foundPost: Post,
        foundUser: User
    ) = foundPost.likes.map { it.user }.contains(foundUser)
}
