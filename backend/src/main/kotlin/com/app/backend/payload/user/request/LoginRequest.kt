package com.app.backend.payload.user.request

data class LoginRequest(
    val username: String,
    val password: String
)
