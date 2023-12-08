package com.app.backend.payload.token.response

import com.app.backend.payload.MessageResponse
import java.util.Date

data class TokenRefreshInternalResponse(
    var accessTokenCookie: String,
    var accessTokenExpiresAt: Date,
    var message: MessageResponse
)
