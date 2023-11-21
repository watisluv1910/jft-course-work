package com.app.springbackend.controller;

import com.app.springbackend.repo.UserBookmarkRepository;
import com.app.springbackend.repo.UserRepository;
import com.app.springbackend.service.UserBookmarkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookmarkControllerTest {

    @InjectMocks
    private UserBookmarkService userBookmarkService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserBookmarkRepository userBookmarkRepository;

    @Test
    public void BookmarkController_CreateBookmark_ReturnResponseEntityCreated() {
    }

    @Test
    void BookmarkController_DeleteBookmarkById_ReturnResponseEntityNoContent() {
    }

    @Test
    void BookmarkController_FindAllBookmarksByUserDetails_ReturnResponseEntityOk() {
    }
}