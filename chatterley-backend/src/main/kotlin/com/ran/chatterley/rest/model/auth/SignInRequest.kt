package com.ran.chatterley.rest.model.auth

data class SignInRequest(
    val nickname: String,
    val password: String
)
