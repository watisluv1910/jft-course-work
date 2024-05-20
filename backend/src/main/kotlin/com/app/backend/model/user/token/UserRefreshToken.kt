package com.app.backend.model.user.token

import com.app.backend.model.user.User
import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "sn_jwt_refresh_token", schema = "jft_database")
class UserRefreshToken {

    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Id
    @field:Column(name = "id", nullable = false)
    var id: Long? = null

    @field:Column(name = "token", nullable = false)
    var token: String = ""

    @field:Column(name = "date_expiration", nullable = false)
    var dateExpiration: Timestamp? = null

    @field:OneToOne(fetch = FetchType.LAZY)
    @field:JoinColumn(name = "user_id", referencedColumnName = "id")
    lateinit var user: User
}
