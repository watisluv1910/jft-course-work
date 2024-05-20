package com.app.backend.payload.user.response

data class LoginInternalResponse(
    var accessTokenCookie: String,
    var refreshTokenCookie: String,
    var refreshTokenExpirationDate: Long,
    var user: UserInfoResponse
)
