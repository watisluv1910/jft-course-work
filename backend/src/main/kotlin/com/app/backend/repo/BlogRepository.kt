package com.app.backend.repo

import com.app.backend.model.Blog
import com.app.backend.model.user.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.NoSuchElementException

/**
 * @author Vladislav Nasevich
 */
@Repository
interface BlogRepository: BaseRepository<Blog> {

    fun findAllByAuthor(author: User): MutableList<Blog>

    fun findAllByTitleIsContainingIgnoreCase(title: String): MutableList<Blog>

    fun findOneById(id: Long): Blog {
        return findByIdOrNull(id) ?: throw NoSuchElementException(
            "Blog with id: $id not found. " +
                    "It may be deleted or not yet created."
        )
    }
}
