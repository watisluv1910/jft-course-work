package com.app.springbackend.service;

import com.app.springbackend.model.bookmark.UserBookmark;
import com.app.springbackend.model.user.User;
import com.app.springbackend.payload.request.AddBookmarkRequest;
import com.app.springbackend.payload.response.MessageResponse;
import com.app.springbackend.repo.UserBookmarkRepository;
import com.app.springbackend.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserBookmarkServiceTest {

    @InjectMocks
    private UserBookmarkService userBookmarkService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserBookmarkRepository userBookmarkRepository;

    @Test
    void UserBookmarkService_CreateBookmark_ReturnCreated() {
        AddBookmarkRequest request = AddBookmarkRequest
                .builder()
                .articleTitle("Example article")
                .articleUrl("https://example.com/article")
                .build();

        Long userId = 1L, bookmarkId = 1L;

        User user = User
                .builder()
                .id(userId)
                .username("TestUser")
                .build();

        UserBookmark bookmark = UserBookmark
                .builder()
                .id(bookmarkId)
                .articleTitle("Example article")
                .articleUrl("https://example.com/article".getBytes())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .user(user)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userBookmarkRepository.save(Mockito.any(UserBookmark.class))).thenReturn(bookmark);

        UserBookmark createdBookmark = userBookmarkService.createBookmark(request, userId);

        assertNotNull(createdBookmark);
        assertEquals(bookmarkId, createdBookmark.getId().longValue());
    }

    @Test
    void UserBookmarkService_CreateBookmark_ThrowIllegalArgumentException_OnUserNotExists() {
        AddBookmarkRequest request = AddBookmarkRequest
                .builder()
                .articleTitle("Example article")
                .articleUrl("https://example.com/article")
                .build();

        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userBookmarkService.createBookmark(request, userId);
        }, "IllegalArgumentException was expected");

        Assertions.assertEquals("Error: User not found", thrown.getMessage());
    }

    @Test
    void UserBookmarkService_DeleteBookmarkById_ReturnMessageResponse() {
        Long userId = 1L, bookmarkId = 1L;

        User user = User
                .builder()
                .id(userId)
                .username("TestUser")
                .build();

        UserBookmark bookmark = UserBookmark
                .builder()
                .id(bookmarkId)
                .articleTitle("Example article")
                .articleUrl("https://example.com/article".getBytes())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .user(user)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userBookmarkRepository.findById(bookmarkId)).thenReturn(Optional.of(bookmark));

        MessageResponse response = userBookmarkService.deleteById(bookmarkId, userId);

        assertNotNull(response);
        assertEquals("Bookmark deleted successfully", response.getMessage());
    }

    @Test
    void UserBookmarkService_DeleteBookmarkById_ThrowIllegalArgumentException_OnUserNotExists() {
        Long userId = 1L, bookmarkId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userBookmarkService.deleteById(bookmarkId, userId);
        }, "IllegalArgumentException was expected");

        Assertions.assertEquals(
                "Error: User not found",
                thrown.getMessage()
        );
    }

    @Test
    void UserBookmarkService_DeleteBookmarkById_ThrowIllegalArgumentException_OnBookmarkNotExists() {
        Long userId = 1L, bookmarkId = 1L;

        User user = User
                .builder()
                .id(userId)
                .username("TestUser")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userBookmarkRepository.findById(bookmarkId)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userBookmarkService.deleteById(bookmarkId, userId);
        }, "IllegalArgumentException was expected");

        Assertions.assertEquals(
                "Error: Bookmark not found with id: " + bookmarkId,
                thrown.getMessage()
        );
    }

    @Test
    void UserBookmarkService_DeleteBookmarkById_ThrowRuntimeException_OnUserNotAuthorized() {
        Long userId = 1L, bookmarkId = 1L, unauthorizedUserId = 2L;

        User user1 = User
                .builder()
                .id(userId)
                .username("TestUser")
                .build();

        User unauthorizedUser = User
                .builder()
                .id(unauthorizedUserId)
                .username("UnauthorizedTestUser")
                .build();

        UserBookmark bookmark = UserBookmark
                .builder()
                .id(bookmarkId)
                .articleTitle("Example article")
                .articleUrl("https://example.com/article".getBytes())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .user(unauthorizedUser)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        when(userBookmarkRepository.findById(bookmarkId)).thenReturn(Optional.of(bookmark));

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            userBookmarkService.deleteById(bookmarkId, userId);
        }, "RuntimeException was expected");

        Assertions.assertEquals(
                "Error: User is not authorized to remove this bookmark",
                thrown.getMessage()
        );
    }

    @Test
    void UserBookmarkService_FindAllBookmarksByUserId_ReturnListOfUserBookmarks() {
        Long userId = 1L;

        User user = User
                .builder()
                .id(userId)
                .username("TestUser_1")
                .build();

        List<UserBookmark> userBookmarks = Arrays.asList(
                UserBookmark
                        .builder()
                        .id(1L)
                        .articleTitle("Example article 1")
                        .articleUrl("https://example.com/article".getBytes())
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .user(user)
                        .build(),
                UserBookmark
                        .builder()
                        .id(2L)
                        .articleTitle("Example article 2")
                        .articleUrl("https://example.com/article".getBytes())
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .user(user)
                        .build()
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userBookmarkRepository.findAllByUserId(userId)).thenReturn(userBookmarks);

        List<UserBookmark> foundBookmarks = userBookmarkService.findAllByUserId(userId);

        assertNotNull(foundBookmarks);
        assertEquals(2, foundBookmarks.size());
    }

    @Test
    void UserBookmarkService_FindAllBookmarksByUserId_ThrowIllegalArgumentException_OnUserNotExists() {
        Long userId = 1L;

        User user = User
                .builder()
                .id(userId)
                .username("TestUser_1")
                .build();

        List<UserBookmark> userBookmarks = Arrays.asList(
                UserBookmark
                        .builder()
                        .id(1L)
                        .articleTitle("Example article 1")
                        .articleUrl("https://example.com/article".getBytes())
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .user(user)
                        .build(),
                UserBookmark
                        .builder()
                        .id(2L)
                        .articleTitle("Example article 2")
                        .articleUrl("https://example.com/article".getBytes())
                        .timestamp(new Timestamp(System.currentTimeMillis()))
                        .user(user)
                        .build()
        );

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<UserBookmark> foundBookmarks = userBookmarkService.findAllByUserId(userId);
        }, "IllegalArgumentException was expected");

        Assertions.assertEquals(
                "Error: User not found",
                thrown.getMessage()
        );
    }
}