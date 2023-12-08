package com.app.backend.payload.token.response

import java.util.Date

data class TokenExpirationResponse(
    var accessTokenExpiresAt: Date,
    var refreshTokenExpiresAt: Date
)
