package com.app.backend.model.post

import com.app.backend.model.Blog
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

/**
 * @author Vladislav Nasevich
 */
@Entity
@Table(name = "sr_post", schema = "tsr_database")
class Post {

    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id
    @field:Column(name = "id", nullable = false)
    var id: Long? = null

    @field:Column(name = "post_status", nullable = false)
    var postStatus: EPostStatus = EPostStatus.POST_STATUS_SUBMITTED

    @field:Column(name = "post_title", nullable = false)
    var postTitle: String = ""

    @field:Column(name = "post_description", nullable = false)
    var postDescription: String = ""

    @field:Column(name = "post_like_count", nullable = false)
    var postLikeCount: Long = 0

    @field:Column(name = "creation_date", nullable = false)
    var creationDate: Timestamp = Timestamp(Date().time)

    @field:OneToOne(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.ALL],
        optional = false
    )
    @field:PrimaryKeyJoinColumn
    lateinit var postMeta: PostMeta

    @field:JsonBackReference
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "blog_id", nullable = false)
    lateinit var blog: Blog

    @field:ManyToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH]
    )
    @field:JoinTable(
        name = "sr_post_categories",
        joinColumns = [JoinColumn(
            name = "post_id",
            referencedColumnName = "id"
        )],
        inverseJoinColumns = [JoinColumn(
            name = "category_id",
            referencedColumnName = "id"
        )]
    )
    @field:JsonIgnoreProperties("posts")
    var categories: MutableSet<PostCategory> = mutableSetOf()

    @field:OneToMany(
        mappedBy = "post",
        fetch = FetchType.EAGER,
        cascade = [CascadeType.REMOVE]
    )
    @field:JsonManagedReference
    var likes: MutableSet<PostLike> = mutableSetOf()
}
