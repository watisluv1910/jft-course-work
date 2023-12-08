package com.app.backend.repo

import com.app.backend.model.bookmark.UserBookmark
import org.springframework.stereotype.Repository

/**
 * Repository interface for managing user bookmarks using Spring Data JPA.
 *
 * This interface extends [BaseRepository] and provides methods for querying user bookmarks.
 *
 * @see BaseRepository
 * @property [UserBookmark] entity type representing a user bookmark.
 */
@Repository
interface UserBookmarkRepository: BaseRepository<UserBookmark> {

    /**
     * Finds all user bookmarks associated with the specified user ID.
     *
     * @param userId the ID of the [user][com.app.backend.model.user.User].
     * @return a mutable list of [UserBookmark] instances associated with the given user ID,
     *         or null if none are found.
     */
    fun findAllByUserId(userId: Long): MutableList<UserBookmark>?
}