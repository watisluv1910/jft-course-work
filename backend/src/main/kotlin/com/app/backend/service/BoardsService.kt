package com.app.backend.service

import com.app.backend.exception.PrincipalNotFoundException
import com.app.backend.payload.user.response.UserInfoResponse
import com.app.backend.repo.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

/**
 * @author Vladislav Nasevich
 */
@Service
class BoardsService(
    val userRepository: UserRepository,
) {

    fun users() = userRepository.findAll().map { UserInfoResponse.build(it) }

    fun deleteUserById(toDeleteId: Long, authentication: Authentication) {

        val currentUser = userRepository
            .findByUsername(authentication.principal.toString())
            ?: throw PrincipalNotFoundException(
                "Authenticated user not found by username: ${authentication.principal}"
            )

        val userToDelete = userRepository
            .findById(toDeleteId)
            .orElseThrow {
                NoSuchElementException(
                    "User to delete not found by id: $toDeleteId"
                )
            }

        if (currentUser.username.equals(userToDelete.username))
            throw IllegalArgumentException("User can't delete himself")

        userRepository.delete(userToDelete)
    }
}
