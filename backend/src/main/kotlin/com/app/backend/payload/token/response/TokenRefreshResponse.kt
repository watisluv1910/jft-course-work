package com.app.backend.payload.token.response

import com.app.backend.payload.MessageResponse
import java.util.*

data class TokenRefreshResponse(
    var accessTokenExpiresAt: Date,
    var message: MessageResponse
)
