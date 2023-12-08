package com.app.backend.repo

import com.app.backend.model.user.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Repository

/**
 * Repository interface for managing user entities using Spring Data JPA.
 *
 * This interface extends [BaseRepository] and provides methods for querying user entities.
 *
 * @see BaseRepository
 * @property [User] the entity type representing a user.
 */
@Repository
interface UserRepository: BaseRepository<User> {

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for.
     * @return [User] associated with the given username, null if not found.
     */
    fun findByUsername(username: String?): User?

    /**
     * Finds a user by their username, throws an exception if not found.
     *
     * @param username the username to search for.
     * @return [User] associated with the given username.
     * @throws UsernameNotFoundException if the user with the specified username is not found.
     */
    fun findOneByUsername(username: String?): User {
        return findByUsername(username)
            ?: throw UsernameNotFoundException("User with username $username not found")
    }

    /**
     * Checks if a user with the specified username exists.
     *
     * @param username the username to check.
     * @return true if a user with the given username exists, false otherwise.
     */
    fun existsByUsername(username: String?): Boolean

    /**
     * Checks if a user with the specified email exists.
     *
     * @param userEmail the email to check.
     * @return true if a user with the given email exists, false otherwise.
     */
    fun existsByUserEmail(userEmail: String?): Boolean
}