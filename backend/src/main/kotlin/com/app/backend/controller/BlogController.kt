package com.app.backend.controller

import com.app.backend.payload.blog.request.CreateBlogRequest
import com.app.backend.payload.blog.response.BlogInfoResponse
import com.app.backend.service.BlogService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Vladislav Nasevich
 */
@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/blog")
class BlogController(
    val blogService: BlogService
) {

    @PostMapping("/add")
    fun addBlog(
        @RequestBody request: CreateBlogRequest,
        @AuthenticationPrincipal authorUsername: String
    ): ResponseEntity<*> {
        return try {
            ResponseEntity<BlogInfoResponse>(
                blogService.createBlog(
                    request,
                    authorUsername
                ), HttpStatus.CREATED
            )
        } catch (ex: Exception) {
            ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }
}
