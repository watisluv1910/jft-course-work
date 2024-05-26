package com.app.backend.payload.user.response

import com.app.backend.model.user.User
import com.app.backend.model.user.role.EUserRole
import com.fasterxml.jackson.annotation.JsonProperty

data class UserInfoResponse(
    var id: Long,
    var username: String,
    var userEmail: String,
    @JsonProperty("roles") var roleNames: List<EUserRole>
) {
    companion object {
        fun build(user: User) =
            UserInfoResponse(
                user.id!!,
                user.username,
                user.userEmail,
                user.roles.map { it.roleName }
            )
    }
}
