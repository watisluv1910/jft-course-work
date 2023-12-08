package com.app.backend.payload.user.response

import com.app.backend.payload.token.response.TokenExpirationResponse

data class LoginResponse(
    var user: UserInfoResponse,
    var tokenExpiration: TokenExpirationResponse
)