package com.app.backend.controller

import com.app.backend.payload.blog.request.UpsertBlogRequest
import com.app.backend.payload.blog.response.BlogInfoBriefResponse
import com.app.backend.payload.blog.response.BlogInfoResponse
import com.app.backend.service.BlogService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * @author Vladislav Nasevich
 */
@RestController
@RequestMapping("/api/blog")
class BlogController(
    val blogService: BlogService
) {

    @PostMapping("/add")
    fun addBlog(
        @RequestBody request: UpsertBlogRequest,
        @AuthenticationPrincipal authorUsername: String
    ): ResponseEntity<*> {
        return try {
            ResponseEntity<BlogInfoBriefResponse>(
                blogService.createBlog(
                    request,
                    authorUsername
                ), HttpStatus.CREATED
            )
        } catch (ex: Exception) {
            ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping
    fun getBlogsByTitle(
        @RequestParam(name = "q") query: String?
    ): ResponseEntity<*> {
        return try {
            ResponseEntity<List<BlogInfoBriefResponse>>(
                query?.let { blogService.findAllByTitle(query) } ?: blogService.findAll(),
                HttpStatus.OK
            )
        } catch (ex: Exception) {
            ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{id}")
    fun getBlog(
        @PathVariable(name = "id") blogId: Long
    ): ResponseEntity<*> {
        return try {
            ResponseEntity<BlogInfoResponse>(
                blogService.findById(blogId),
                HttpStatus.OK
            )
        } catch (ex: Exception) {
            ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("/{id}")
    fun editBlog(
        @PathVariable(name = "id") blogId: Long,
        @RequestBody request: UpsertBlogRequest,
        @AuthenticationPrincipal editorUsername: String
    ): ResponseEntity<*> {
        return try {
            ResponseEntity<BlogInfoResponse>(
                blogService.updateBlogInfo(blogId, request, editorUsername),
                HttpStatus.OK
            )
        } catch (ex: Exception) {
            ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteBlog(
        @PathVariable(name = "id") blogId: Long,
        @AuthenticationPrincipal username: String
    ): ResponseEntity<String> {
        return try {
            blogService.deleteBlog(blogId, username)
            ResponseEntity.ok().build()
        } catch (ex: Exception) {
            ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }
}
