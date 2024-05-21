package com.app.backend.repo

import com.app.backend.model.Blog
import com.app.backend.model.user.User
import org.springframework.stereotype.Repository

/**
 * @author Vladislav Nasevich
 */
@Repository
interface BlogRepository: BaseRepository<Blog> {

    fun findAllByAuthor(author: User): MutableList<Blog>?

    fun findAllByLastEditor(lastEditor: User): MutableList<Blog>?

    fun findAllByTitleIsContainingIgnoreCase(title: String): MutableList<Blog>?
}
