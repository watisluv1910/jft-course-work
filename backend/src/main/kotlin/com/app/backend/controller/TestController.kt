package com.app.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller for testing role-based access control.
 * Defines endpoints that can be accessed depending on user's role.
 */
@RestController
@RequestMapping("/api/test")
class TestController {

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
    fun adminAccess(): ResponseEntity<String> {
        return ResponseEntity.ok("Admin content")
    }
}
