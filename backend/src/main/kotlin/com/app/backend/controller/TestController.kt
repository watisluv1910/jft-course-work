package com.app.backend.controller

import com.app.backend.payload.user.response.UserInfoResponse
import com.app.backend.repo.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

/**
 * Controller for testing role-based access control.
 * Defines endpoints that can be accessed depending on user's role.
 *
 * @author Vladislav Nasevich
 */
@RestController
@RequestMapping("/api/test")
class TestController(
    val userRepository: UserRepository
) {

    /**
     * Endpoint that can be accessed by users with the role USER, MODERATOR, or ADMIN.
     *
     * @return [ResponseEntity] with "User content" message.
     */
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    fun userAccess(): ResponseEntity<String> {
        return ResponseEntity.ok("User content")
    }

    /**
     * Endpoint that can be accessed by users with the role MODERATOR.
     *
     * @return [ResponseEntity] with "Moderator content" message.
     */
    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR')")
    fun moderatorAccess(): ResponseEntity<List<UserInfoResponse>> =
        ResponseEntity(
            userRepository.findAll().map { UserInfoResponse.build(it) },
            HttpStatus.OK
        )

    @DeleteMapping("/moderator/deleteUser/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    fun deleteUser(@PathVariable id: Long, authentication: Authentication): ResponseEntity<Any> {
        val currentUser = userRepository
            .findByUsername(authentication.principal.toString())
            ?: return ResponseEntity.notFound().build()
        val userToDelete = userRepository
            .findById(id)
            .orElse(null) ?: return ResponseEntity.notFound().build()

        if (!currentUser.username.equals(userToDelete.username)) {
            userRepository.delete(userToDelete)
            return ResponseEntity.ok().build()
        } else {
            return ResponseEntity.badRequest().body("Moderator cannot delete themselves.")
        }
    }

    /**
     * Endpoint that can be accessed by users with the role ADMIN.
     *
     * @return [ResponseEntity] with "Admin content" message.
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess(): ResponseEntity<String> {
        return ResponseEntity.ok("Admin content")
    }
}
