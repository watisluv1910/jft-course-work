package com.app.backend.model.post

import com.app.backend.model.user.User
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

/**
 * @author Vladislav Nasevich
 */
@Entity
@Table(name = "sr_post_category", schema = "tsr_database")
class PostCategory {

    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id
    @field:Column(name = "id", nullable = false)
    var id: Long? = null

    @field:Column(name = "category_name", nullable = false, unique = true)
    var categoryName: String? = null

    @field:ManyToMany(
        mappedBy = "categories",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH]
    )
    @field:JsonIgnoreProperties("categories")
    var posts: MutableSet<Post> = mutableSetOf()
}
