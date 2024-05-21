package com.app.backend.payload.user.response

import com.app.backend.model.user.User

/**
 *   @author Vladislav Nasevich
 */
data class UserInfoBriefResponse(
    var id: Long,
    var username: String
) {
    companion object {
        fun build(user: User) =
            UserInfoBriefResponse(
                user.id!!,
                user.username
            )
    }
}
