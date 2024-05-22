package com.app.backend.model.post

import com.app.backend.model.user.User
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

/**
 * @author Vladislav Nasevich
 */
@Entity
@Table(name = "sr_post_meta", schema = "tsr_database")
class PostMeta {

    @field:Id
    @field:Column(name = "post_id", nullable = false)
    var id: Long? = null

    @field:Column(name = "post_body", nullable = false)
    var postBody: String = ""

    @field:Column(name = "last_edit_date", nullable = false)
    var lastEditDate: Timestamp = Timestamp(Date().time)

    @field:JsonBackReference
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "last_editor_id", nullable = false)
    lateinit var lastEditor: User

    @field:MapsId
    @field:OneToOne
    @JoinColumn(name = "post_id")
    lateinit var post: Post
}
