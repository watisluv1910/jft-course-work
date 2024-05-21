package com.app.backend.model.post

import com.app.backend.model.user.User
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.sql.Timestamp

/**
 * @author Vladislav Nasevich
 */
@Entity
@Table(name = "sr_post_like", schema = "tsr_database")
class PostLike {

    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id
    @field:Column(name = "id", nullable = false)
    var id: Long? = null

    @field:Column(name = "creation_date", nullable = false)
    var creationDate: Timestamp? = null

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User? = null

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    val post: Post? = null
}
