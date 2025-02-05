package com.ran.chatterley.db.model

import java.time.Instant

data class User(
    val id: String,
    val nickname: String,
    val passwordHash: String,
    val createTime: Instant
)
