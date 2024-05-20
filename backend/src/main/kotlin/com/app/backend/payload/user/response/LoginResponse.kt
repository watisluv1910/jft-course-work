package com.app.backend.payload.user.response

data class LoginResponse(
    var user: UserInfoResponse,
    var refreshTokenExpirationDate: Long
)
