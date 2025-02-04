package com.ran.chatterley.rest.model.user

data class UserResponse(
    val user: User
)

data class User(
    val id: String,
    val nickname: String
)
