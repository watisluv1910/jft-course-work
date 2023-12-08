package com.app.backend.model.user

import com.app.backend.model.bookmark.UserBookmark
import com.app.backend.model.user.role.UserRole
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*


@Entity
@Table(name = "sn_user", schema = "jft_database")
class User {

    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id
    @field:Column(name = "id", nullable = false)
    var id: Long? = null

    @field:Column(name = "username", nullable = false)
    var username: String? = null

    @field:Column(name = "password", nullable = false)
    var password: String? = null

    @field:Column(name = "user_email", nullable = false)
    var userEmail: String? = null

    @field:ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH]
    )
    @field:JoinTable(
        name = "sn_user_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    @field:JsonIgnoreProperties("users")
    var roles: MutableSet<UserRole> = mutableSetOf()

    @field:OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @field:JsonManagedReference
    var bookmarks: MutableSet<UserBookmark> = mutableSetOf()
}

