package com.ran.chatterley.service.auth

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@SpringBootTest
class PasswordHashServiceTest {

    // todo: find out how to call suspend functions from unit tests

    @Autowired
    private lateinit var passwordHashService: PasswordHashService

    @Test
    fun shouldEvaluateDeterministically() {
        val randomPasswordsList = (1 .. 10).map { UUID.randomUUID().toString() }
        randomPasswordsList.forEach { password ->
            val firstlyEvaluatedPasswordHash = passwordHashService.evaluatePasswordHash(password)
            val secondlyEvaluatedPasswordHash = passwordHashService.evaluatePasswordHash(password)
            Assertions.assertThat(firstlyEvaluatedPasswordHash).isEqualTo(secondlyEvaluatedPasswordHash)
        }
    }
}
