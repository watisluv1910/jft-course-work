package com.app.backend.controller

import com.app.backend.payload.user.response.UserInfoResponse
import com.app.backend.service.BoardsService
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
@RequestMapping("/api/board")
class BoardsController(
    val boardsService: BoardsService
) {

    /**
     * Endpoint that can be accessed by users with the role USER, MODERATOR, or ADMIN.
     *
     * @return [ResponseEntity] with "User content" message.
     */
    @GetMapping("/author")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    fun userAccess(): ResponseEntity<String> {
        return ResponseEntity.ok("Author content")
    }

    /**
     * Endpoint that can be accessed by users with the role MODERATOR.
     *
     * @return [ResponseEntity] with "Moderator content" message.
     */
    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR')")
    fun moderatorAccess(): ResponseEntity<String> {
        return ResponseEntity.ok("Moderator content")
    }

    /**
     * Endpoint that can be accessed by users with the role ADMIN.
     *
     * @return [ResponseEntity] with "Admin content" message.
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess(): ResponseEntity<List<UserInfoResponse>> =
        ResponseEntity
            .ok()
            .body(boardsService.users())

    @DeleteMapping("/admin/deleteUser/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteUser(@PathVariable id: Long, authentication: Authentication): ResponseEntity<Any> {
        try {
            boardsService.deleteUserById(id, authentication)
            return ResponseEntity.ok().build()
        } catch (ex: RuntimeException) {
            return ResponseEntity.badRequest().body(ex.message)
        }
    }
}
