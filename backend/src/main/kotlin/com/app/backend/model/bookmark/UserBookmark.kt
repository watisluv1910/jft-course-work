package com.app.backend.model.bookmark

import com.app.backend.model.user.User
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "sn_user_bookmark", schema = "jft_database")
class UserBookmark {

    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id
    @field:Column(name = "id", nullable = false)
    var id: Long? = null

    @field:Column(name = "article_url", nullable = false)
    var articleUrl: String? = null

    @field:Column(name = "article_title", nullable = false)
    var articleTitle: String? = null

    @field:Column(name = "timestamp", nullable = false)
    var timestamp: Timestamp? = null

    @field:JsonBackReference
    @field:ManyToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User
}
