package com.app.backend.controller

import com.app.backend.model.bookmark.ArticleBookmark
import com.app.backend.payload.MessageResponse
import com.app.backend.payload.bookmark.request.CreateArticleBookmarkRequest
import com.app.backend.service.ArticleBookmarkService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * Controller for managing bookmarks for authenticated users.
 *
 * Provides endpoints for adding, deleting, and retrieving bookmarks for the authenticated user.
 * All endpoints are secured with the 'USER' role using [PreAuthorize].
 *
 * @property articleBookmarkService The service for managing user bookmarks.
 * @author Vladislav Nasevich
 */
@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/user/bookmarks")
class ArticleBookmarkController(
    val articleBookmarkService: ArticleBookmarkService
) {

    /**
     * Adds a new bookmark for the authenticated user.
     *
     * @param request [CreateArticleBookmarkRequest] containing details of the article to be bookmarked.
     * @param username username of the authenticated user obtained from the [AuthenticationPrincipal].
     * @return A [ResponseEntity] with the newly created [ArticleBookmark] and HTTP status code 201.
     */
    @PostMapping("/add")
    fun addBookmark(
        @RequestBody request: CreateArticleBookmarkRequest,
        @AuthenticationPrincipal username: String
    ) = ResponseEntity<ArticleBookmark>(
        articleBookmarkService.createBookmark(request, username),
        HttpStatus.CREATED
    )

    /**
     * Deletes a bookmark for the authenticated user by bookmark ID.
     *
     * @param bookmarkId ID of the bookmark to be deleted.
     * @param username username of the authenticated user obtained from the [AuthenticationPrincipal].
     * @return A [ResponseEntity] with a [MessageResponse] and HTTP status code 204 upon successful deletion.
     */
    @DeleteMapping("/delete/{bookmarkId}")
    fun deleteBookmark(
        @PathVariable bookmarkId: Long,
        @AuthenticationPrincipal username: String
    ) = ResponseEntity<MessageResponse>(
        articleBookmarkService.deleteById(bookmarkId, username),
        HttpStatus.NO_CONTENT
    )

    /**
     * Retrieves all bookmarks for the authenticated user.
     *
     * @param username username of the authenticated user obtained from the [AuthenticationPrincipal].
     * @return A [ResponseEntity] with a list of [ArticleBookmark] objects and HTTP status code 200.
     */
    @GetMapping
    fun getAllBookmarksForUser(
        @AuthenticationPrincipal username: String
    ) = ResponseEntity<List<ArticleBookmark>>(
        articleBookmarkService.findAllByUsername(username),
        HttpStatus.OK
    )
}
