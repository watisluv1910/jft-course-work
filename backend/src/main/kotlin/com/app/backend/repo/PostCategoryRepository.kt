package com.app.backend.repo

import com.app.backend.model.post.PostCategory
import org.springframework.stereotype.Repository

/**
 * @author Vladislav Nasevich
 */
@Repository
interface PostCategoryRepository: BaseRepository<PostCategory> {

    fun existsByCategoryName(categoryName: String): Boolean

    fun findFirstByCategoryNameIgnoreCase(categoryName: String): PostCategory?

    fun findAllByCategoryNameIsContainingIgnoreCase(categoryName: String): MutableList<PostCategory>?

    fun findAllByCategoryNameIn(categoryNames: MutableSet<String>): MutableList<PostCategory>?
}
