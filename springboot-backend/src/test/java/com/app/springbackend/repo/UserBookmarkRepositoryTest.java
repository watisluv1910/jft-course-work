package com.app.springbackend.repo;

import com.app.springbackend.model.bookmark.UserBookmark;
import com.app.springbackend.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//class UserBookmarkRepositoryTest {

//    @Autowired
//    UserBookmarkRepository userBookmarkRepository;
//
//    @Test
//    void UserBookmarkRepository_FindAllBookmarksByUserId_ReturnListOfUserBookmarks() {
//        long userId = 1L;
//
//        User user = User
//                .builder()
//                .id(userId)
//                .username("TestUser_1")
//                .build();
//
//        UserBookmark
//                inBookmark1 = UserBookmark
//                        .builder()
//                        .id(1L)
//                        .articleTitle("Example article 1")
//                        .articleUrl("https://example.com/article".getBytes())
//                        .timestamp(new Timestamp(System.currentTimeMillis()))
//                        .user(user)
//                        .build(),
//                inBookmark2 = UserBookmark
//                        .builder()
//                        .id(2L)
//                        .articleTitle("Example article 2")
//                        .articleUrl("https://example.com/article".getBytes())
//                        .timestamp(new Timestamp(System.currentTimeMillis()))
//                        .user(user)
//                        .build(),
//                outBookmark = UserBookmark
//                        .builder()
//                        .id(3L)
//                        .articleTitle("Example article 3")
//                        .articleUrl("https://example.com/article".getBytes())
//                        .timestamp(new Timestamp(System.currentTimeMillis()))
//                        .user(User
//                                .builder()
//                                .id(userId + 1)
//                                .username("TestUser_2")
//                                .build())
//                        .build();
//
//        userBookmarkRepository.saveAll(Arrays.asList(
//                inBookmark1,
//                inBookmark2,
//                outBookmark
//        ));
//
//        List<UserBookmark> foundUserBookmarks = userBookmarkRepository.findAllByUserId(userId);
//
//        assertNotNull(foundUserBookmarks);
//        assertThat(foundUserBookmarks).hasSize(2).contains(inBookmark1, inBookmark2);
//    }
//}