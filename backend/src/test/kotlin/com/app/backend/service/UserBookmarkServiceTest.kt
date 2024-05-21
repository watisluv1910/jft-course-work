package com.app.backend.service

import com.app.backend.model.bookmark.UserBookmark
import com.app.backend.model.user.User
import com.app.backend.payload.bookmark.request.CreateBookmarkRequest
import com.app.backend.repo.UserBookmarkRepository
import com.app.backend.repo.UserRepository
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.sql.Timestamp

@ExtendWith(MockKExtension::class)
@MockKExtension.ConfirmVerification
class UserBookmarkServiceTest {

    private val mockUserRepository: UserRepository = mockk()
    private val mockUserBookmarkRepository: UserBookmarkRepository = mockk()
    private val mockUserBookmarkService = UserBookmarkService(mockUserRepository, mockUserBookmarkRepository)

    @Test
    fun userBookmarkService_whenGetAllBookmarks_thenReturnAllBookmarks() {
        val testUser = User().apply {
            id = 1
            username = "TestUserName"
            userEmail = "TestUserEmail"
        }
        val testBookmark = UserBookmark().apply {
            articleUrl = "https://example.com/article"
            articleTitle = "Example article"
            creationDate = Timestamp(System.currentTimeMillis())
            user = testUser
        }

        val testBookmarks: MutableList<UserBookmark> = mutableListOf()
        for (i in 1..10) {
            testBookmarks.add(testBookmark.apply { id = i.toLong() })
        }

        // Given
        every { mockUserRepository.getExisted(1) } returns testUser
        every { mockUserBookmarkRepository.findAllByUserId(1) } returns testBookmarks

        // When
        val result = mockUserBookmarkService.findAllByUserId(1)

        // Then
        verify(exactly = 1) { mockUserRepository.getExisted(any()) }
        verify(exactly = 1) { mockUserBookmarkRepository.findAllByUserId(any()) }
        confirmVerified(mockUserRepository, mockUserBookmarkRepository)
        assertEquals(result, testBookmarks)
    }

    @Test
    fun userBookmarkService_whenCreateBookmarkByUsername_thenReturnCreated() {
        val userId = 1L
        val bookmarkId = 1L
        val request = CreateBookmarkRequest(
            "https://example.com/article",
            "Example article"
        )
        val testUser = User().apply {
            id = userId
            username = "TestUserName"
            userEmail = "TestUserEmail"
        }
        val testBookmark = UserBookmark().apply {
            articleTitle = "Example article"
            articleUrl = "https://example.com/article"
            creationDate = Timestamp(System.currentTimeMillis())
            user = testUser
        }

        // Given
        every { mockUserRepository.findOneByUsername(testUser.username) } returns testUser
        every { mockUserBookmarkRepository.save(any()) } returns UserBookmark().apply {
            id = bookmarkId
            articleTitle = testBookmark.articleTitle
            articleUrl = testBookmark.articleUrl
            creationDate = testBookmark.creationDate
            user = testBookmark.user
        }

        // When
        val createdBookmark = mockUserBookmarkService.createBookmark(request, testUser.username!!)

        // Then
        verify(exactly = 1) { mockUserRepository.findOneByUsername(any()) }
        verify(exactly = 1) { mockUserBookmarkRepository.save(any()) }
        confirmVerified(mockUserRepository, mockUserBookmarkRepository)
        Assertions.assertNotNull(createdBookmark)
        assertEquals(bookmarkId, createdBookmark.id)
        assertEquals(testBookmark.articleTitle, createdBookmark.articleTitle)
    }
}
