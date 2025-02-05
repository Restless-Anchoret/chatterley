package com.ran.chatterley.rest.model.auth

data class SignUpRequest(
    val nickname: String,
    val password: String
)
