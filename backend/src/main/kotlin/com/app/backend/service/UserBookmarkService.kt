package com.app.backend.service

import com.app.backend.model.bookmark.UserBookmark
import com.app.backend.model.user.User
import com.app.backend.payload.MessageResponse
import com.app.backend.payload.bookmark.request.CreateBookmarkRequest
import com.app.backend.repo.UserBookmarkRepository
import com.app.backend.repo.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.NoSuchElementException

/**
 * Service for managing bookmarks of users.
 *
 * Provides methods to add, delete, and retrieve bookmarks for a specific user.
 *
 * @property userRepository The repository for managing user entities.
 * @property userBookmarkRepository The repository for managing user bookmarks.
 */
@Service
class UserBookmarkService(
    private val userRepository: UserRepository,
    private val userBookmarkRepository: UserBookmarkRepository
) {

    /**
     * Creates a new bookmark for the specified user.
     *
     * @param request [CreateBookmarkRequest] containing details of the article to be bookmarked.
     * @param user [User] for whom the bookmark is to be created.
     * @return [UserBookmark] object representing the newly created bookmark.
     */
    fun createBookmark(
        request: CreateBookmarkRequest,
        user: User
    ): UserBookmark {
        return userBookmarkRepository.save(
            UserBookmark().apply {
                this.user = user
                this.articleUrl = request.articleUrl
                this.articleTitle = request.articleTitle
                this.timestamp = Timestamp(System.currentTimeMillis())
            }
        )
    }

    /**
     * Creates a new bookmark for the user with the specified username.
     *
     * @param request [CreateBookmarkRequest] containing details of the article to be bookmarked.
     * @param username username of the user for whom the bookmark is to be created.
     * @return [UserBookmark] object representing the newly created bookmark.
     */
    fun createBookmark(
        request: CreateBookmarkRequest,
        username: String
    ): UserBookmark {
        val user = userRepository.findOneByUsername(username)
        return createBookmark(request, user)
    }

    /**
     * Deletes a specified bookmark for a given user.
     *
     * @param bookmarkId ID of the bookmark to be deleted.
     * @param user user for whom the bookmark is to be deleted.
     * @return [MessageResponse] object containing a success message upon deletion.
     * @throws NoSuchElementException if the bookmark is not found.
     * @throws RuntimeException if the user is not authorized to remove the bookmark.
     */
    @Transactional
    fun deleteById(bookmarkId: Long, user: User): MessageResponse {
        val bookmark =
            userBookmarkRepository.findByIdOrNull(bookmarkId)
                ?: throw NoSuchElementException("Error: User bookmark not found")
        if (bookmark.user != user) {
            throw RuntimeException("Error: User is not authorized to remove this bookmark")
        }
        userBookmarkRepository.deleteById(bookmarkId)
        return MessageResponse(
            "Bookmark with id $bookmarkId deleted successfully."
        )
    }

    /**
     * Deletes a specified bookmark for a given user ID.
     *
     * @param bookmarkId ID of the bookmark to be deleted.
     * @param userId ID of the user for whom the bookmark is to be deleted.
     * @return [MessageResponse] object containing a success message upon deletion.
     * @throws NoSuchElementException if the bookmark is not found.
     */
    @Transactional
    fun deleteById(bookmarkId: Long, userId: Long): MessageResponse {
        val user = userRepository.getExisted(userId)
        return deleteById(bookmarkId, user)
    }

    /**
     * Deletes a specified bookmark for a user with the specified username.
     *
     * @param bookmarkId The ID of the bookmark to be deleted.
     * @param username The username of the user for whom the bookmark is to be deleted.
     * @return [MessageResponse] object containing a success message upon deletion.
     * @throws NoSuchElementException if the bookmark is not found.
     */
    @Transactional
    fun deleteById(bookmarkId: Long, username: String): MessageResponse {
        val user = userRepository.findOneByUsername(username)
        return deleteById(bookmarkId, user)
    }

    fun findAll(): List<UserBookmark> = userBookmarkRepository.findAll()

    /**
     * Retrieves all bookmarks for a specified user.
     *
     * @param user user for whom the bookmarks are to be retrieved.
     * @return list of [UserBookmark] objects representing all bookmarks of the user.
     */
    fun findAllByUser(user: User): List<UserBookmark> =
        userBookmarkRepository.findAllByUserId(user.id!!) ?: listOf()

    /**
     * Retrieves all bookmarks for a user with the specified user ID.
     *
     * @param userId ID of the user for whom the bookmarks are to be retrieved.
     * @return list of [UserBookmark] objects representing all bookmarks of the user.
     * @throws NoSuchElementException if the user is not found.
     */
    @Transactional
    fun findAllByUserId(userId: Long): List<UserBookmark> {
        userRepository.getExisted(userId)
        return userBookmarkRepository.findAllByUserId(userId) ?: listOf()
    }

    /**
     * Retrieves all bookmarks for a user with the specified username.
     *
     * @param username username of the user for whom the bookmarks are to be retrieved.
     * @return list of [UserBookmark] objects representing all bookmarks of the user.
     */
    @Transactional
    fun findAllByUsername(username: String): List<UserBookmark> {
        val user = userRepository.findOneByUsername(username)
        return findAllByUser(user)
    }
}
