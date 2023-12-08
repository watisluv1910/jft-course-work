package com.app.backend.payload.user.response

import com.app.backend.payload.token.response.TokenExpirationResponse

data class LoginInternalResponse(
    var accessTokenCookie: String,
    var refreshTokenCookie: String,
    var tokenExpiration: TokenExpirationResponse,
    var user: UserInfoResponse
)
