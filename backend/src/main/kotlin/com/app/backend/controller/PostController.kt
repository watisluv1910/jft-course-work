package com.app.backend.controller

import com.app.backend.payload.post.request.UpsertPostRequest
import com.app.backend.payload.post.response.PostInfoBriefResponse
import com.app.backend.payload.post.response.PostInfoResponse
import com.app.backend.payload.post.response.PostLikeInfoResponse
import com.app.backend.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * @author Vladislav Nasevich
 */
@RestController
@RequestMapping("/api/blog/{blogId}/post")
class PostController(
    private val postService: PostService
) {

    @PostMapping("/add")
    fun addPost(
        @PathVariable blogId: Long,
        @RequestBody request: UpsertPostRequest,
        @AuthenticationPrincipal authorUsername: String
    ): ResponseEntity<*> {
        return try {
            ResponseEntity<PostInfoBriefResponse>(
                postService.createPost(
                    request,
                    blogId,
                    authorUsername
                ), HttpStatus.CREATED
            )
        } catch (ex: Exception) {
            ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{id}")
    fun getPost(
        @PathVariable blogId: Long,
        @PathVariable(name = "id") postId: Long
    ): ResponseEntity<*> {
        return try {
            ResponseEntity<PostInfoResponse>(
                postService.findById(blogId, postId),
                HttpStatus.OK
            )
        } catch (ex: Exception) {
            ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping("/{id}")
    fun editPost(
        @PathVariable blogId: Long,
        @PathVariable(name = "id") postId: Long,
        @RequestBody request: UpsertPostRequest,
        @AuthenticationPrincipal editorUsername: String
    ): ResponseEntity<*> {
        return try {
            ResponseEntity<PostInfoResponse>(
                postService.updatePostInfo(
                    blogId,
                    postId,
                    request,
                    editorUsername
                ),
                HttpStatus.OK
            )
        } catch (ex: Exception) {
            ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/{id}")
    fun deletePost(
        @PathVariable blogId: Long,
        @PathVariable(name = "id") postId: Long,
        @AuthenticationPrincipal username: String
    ): ResponseEntity<String> {
        return try {
            postService.deletePost(blogId, postId, username)
            ResponseEntity.noContent().build()
        } catch (ex: Exception) {
            ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/{id}/like")
    fun likePost(
        @PathVariable blogId: Long,
        @PathVariable(name = "id") postId: Long,
        @AuthenticationPrincipal username: String
    ): ResponseEntity<*> {
        return try {
            ResponseEntity<PostLikeInfoResponse>(
                postService.likePost(blogId, postId, username),
                HttpStatus.OK
            )
        } catch (ex: Exception) {
            ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/{id}/dislike")
    fun dislikePost(
        @PathVariable blogId: Long,
        @PathVariable(name = "id") postId: Long,
        @AuthenticationPrincipal username: String
    ): ResponseEntity<String> {
        return try {
            postService.dislikePost(blogId, postId, username)
            ResponseEntity.noContent().build()
        } catch (ex: Exception) {
            ResponseEntity<String>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }
}
