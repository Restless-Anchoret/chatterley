package com.ran.chatterley.service.auth

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Service

@Service
class PasswordHashService {

    suspend fun evaluatePasswordHash(password: String): String {
        return DigestUtils.sha256Hex(password + HASH_SALT)
    }

    companion object {
        // todo: move the salt to config file
        private val HASH_SALT = "ifjdiaiefhg2838gweg81jhjga"
    }
}
