package com.ran.chatterley.service.common

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class IdGenerator {

    suspend fun generateUuid(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }
}
