package com.app.backend.model.user

import com.app.backend.model.bookmark.ArticleBookmark
import com.app.backend.model.user.role.UserRole
import com.app.backend.model.user.token.UserRefreshToken
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.sql.Timestamp
import java.util.*

@Entity
@Table(name = "sr_user", schema = "tsr_database")
class User {

    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id
    @field:Column(name = "id", nullable = false)
    var id: Long? = null

    @field:Column(name = "username", nullable = false)
    var username: String = ""

    @field:Column(name = "password", nullable = false)
    var password: String = ""

    @field:Column(name = "user_email", nullable = false)
    var userEmail: String = ""

    @field:Column(name = "creation_date", nullable = false)
    var creationDate: Timestamp = Timestamp(Date().time)

    @field:ManyToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH]
    )
    @field:JoinTable(
        name = "sr_user_roles",
        joinColumns = [JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
        )],
        inverseJoinColumns = [JoinColumn(
            name = "role_id",
            referencedColumnName = "id"
        )]
    )
    @field:JsonIgnoreProperties("users")
    var roles: MutableSet<UserRole> = mutableSetOf()

    @field:OneToMany(
        mappedBy = "user",
        fetch = FetchType.EAGER,
        cascade = [CascadeType.REMOVE]
    )
    @field:JsonManagedReference
    var bookmarks: MutableSet<ArticleBookmark> = mutableSetOf()

    @field:OneToOne(
        mappedBy = "user",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE]
    )
    var refreshToken: UserRefreshToken? = null
}
