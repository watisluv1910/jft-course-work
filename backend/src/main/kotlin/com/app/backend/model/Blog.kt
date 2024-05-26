package com.app.backend.model

import com.app.backend.model.post.Post
import com.app.backend.model.user.User
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "sr_blog", schema = "tsr_database")
class Blog {

    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id
    @field:Column(name = "id", nullable = false)
    var id: Long? = null

    @field:Column(name = "blog_title", nullable = false)
    var title: String = ""

    @field:Column(name = "blog_description", nullable = false)
    var description: String = ""

    @field:Column(name = "creation_date", nullable = false)
    var creationDate: Timestamp = Timestamp(Date().time)

    @field:Column(name = "last_edit_date", nullable = false)
    var lastEditDate: Timestamp? = Timestamp(Date().time)

    @field:JsonBackReference
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "author_id", nullable = false)
    lateinit var author: User

    @field:JsonBackReference
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "last_editor_id", nullable = false)
    lateinit var lastEditor: User

    @field:OneToMany(
        mappedBy = "blog",
        fetch = FetchType.EAGER,
        cascade = [CascadeType.REMOVE],
        orphanRemoval = true
    )
    @field:JsonManagedReference
    var posts: MutableSet<Post> = mutableSetOf()

    @PreRemove
    fun preRemove() {
        author.blogs.remove(this)
        lastEditor.blogs.remove(this)
    }
}
