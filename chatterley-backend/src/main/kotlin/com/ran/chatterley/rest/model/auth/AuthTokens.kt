package com.ran.chatterley.rest.model.auth

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String
)
