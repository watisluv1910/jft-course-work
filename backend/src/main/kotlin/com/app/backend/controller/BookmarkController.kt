package com.app.backend.controller

import com.app.backend.model.bookmark.UserBookmark
import com.app.backend.payload.MessageResponse
import com.app.backend.payload.bookmark.request.CreateBookmarkRequest
import com.app.backend.service.UserBookmarkService
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
 * @property userBookmarkService The service for managing user bookmarks.
 */
@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/user/bookmarks")
class BookmarkController(
    val userBookmarkService: UserBookmarkService
) {

    /**
     * Adds a new bookmark for the authenticated user.
     *
     * @param request [CreateBookmarkRequest] containing details of the article to be bookmarked.
     * @param username username of the authenticated user obtained from the [AuthenticationPrincipal].
     * @return A [ResponseEntity] with the newly created [UserBookmark] and HTTP status code 201.
     */
    @PostMapping("/add")
    fun addBookmark(
        @RequestBody request: CreateBookmarkRequest,
        @AuthenticationPrincipal username: String
    ) =
        ResponseEntity<UserBookmark>(
            userBookmarkService.createBookmark(request, username),
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
    ) =
        ResponseEntity<MessageResponse>(
            userBookmarkService.deleteById(bookmarkId, username),
            HttpStatus.NO_CONTENT
        )

    /**
     * Retrieves all bookmarks for the authenticated user.
     *
     * @param username username of the authenticated user obtained from the [AuthenticationPrincipal].
     * @return A [ResponseEntity] with a list of [UserBookmark] objects and HTTP status code 200.
     */
    @GetMapping
    fun getAllBookmarks(
        @AuthenticationPrincipal username: String
    ) = ResponseEntity<List<UserBookmark>>(
            userBookmarkService.findAllByUsername(username),
            HttpStatus.OK
        )
}
