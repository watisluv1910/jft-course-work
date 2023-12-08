package com.app.backend.model.user.role

import com.app.backend.model.user.User
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@Table(name = "sn_user_role", schema = "jft_database")
class UserRole {

    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id
    @field:Column(name = "id", nullable = false)
    var id: Long? = null

    @field:Enumerated(EnumType.STRING)
    @field:Column(name = "role_name", nullable = false)
    var roleName: EUserRole = EUserRole.ROLE_USER

    @field:ManyToMany(
        mappedBy = "roles",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH]
    )
    @field:JsonIgnoreProperties("roles")
    var users: MutableSet<User> = mutableSetOf()
}

