package com.app.backend.payload.token.response

import java.sql.Timestamp

data class TokenRefreshExceptionResponse(
    var statusCode: Int,
    var timestamp: Timestamp,
    var message: String,
    var description: String
)