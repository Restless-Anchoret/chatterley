package com.ran.chatterley.rest.model.auth

data class ChangePasswordRequest(
    val nickname: String,
    val oldPassword: String,
    val newPassword: String
)
