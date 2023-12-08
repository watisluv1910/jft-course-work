package com.app.backend.repo

import com.app.backend.model.user.User
import com.app.backend.model.user.token.UserRefreshToken
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 * Repository interface for managing user refresh tokens using Spring Data JPA.
 *
 * This interface extends [BaseRepository] and provides methods for querying and manipulating user refresh tokens.
 *
 * @see BaseRepository
 * @property [UserRefreshToken] entity type representing a user refresh token.
 */
@Repository
interface UserRefreshTokenRepository: BaseRepository<UserRefreshToken> {

    /**
     * Finds a user refresh token by its token value.
     *
     * @param token the token value to search for.
     * @return [UserRefreshToken] associated with the given token, null if not found.
     */
    fun findByToken(token: String?): UserRefreshToken?

    /**
     * Finds a user refresh token by its token value, throws an exception if not found.
     *
     * @param token the token value to search for.
     * @return [UserRefreshToken] associated with the given token.
     * @throws IllegalArgumentException if the token is not found.
     */
    fun findOneByToken(token: String): UserRefreshToken =
        findByToken(token) ?: throw IllegalArgumentException("Wrong refresh token provided")

    /**
     * Checks if a user refresh token with the specified token value exists.
     *
     * @param token token value to check.
     * @return true if a user refresh token with the given token exists, false otherwise.
     */
    fun existsByToken(token: String): Boolean

    /**
     * Finds a user refresh token associated with the specified user.
     *
     * @param user [User] entity.
     * @return [UserRefreshToken] associated with the given user, null if not found.
     */
    fun findByUser(user: User?): UserRefreshToken?

    /**
     * Deletes user refresh tokens associated with the specified user.
     *
     * @param user [User] entity.
     */
    @Transactional
    @Modifying
    fun deleteByUser(user: User)
}