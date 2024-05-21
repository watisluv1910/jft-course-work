package com.app.backend.model.post

import jakarta.persistence.*
import java.sql.Timestamp

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

    @field:Column(name = "deletion_date", nullable = false)
    var deletionDate: Timestamp? = null

    @field:Column(name = "last_edit_date", nullable = false)
    var lastEditDate: Timestamp? = null

    @field:Column(name = "last_editor_id", nullable = false)
    var lastEditorId: Long? = null

    @field:MapsId
    @field:OneToOne(mappedBy = "postMeta")
    lateinit var post: Post
}
