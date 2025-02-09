package com.ran.chatterley.service.auth

import com.ran.chatterley.db.component.AtomicDbOperation
import com.ran.chatterley.db.dao.UserDao
import com.ran.chatterley.db.model.User
import com.ran.chatterley.rest.model.auth.AuthTokens
import com.ran.chatterley.service.auth.model.JwtTokenType
import com.ran.chatterley.service.auth.model.UserAuthData
import com.ran.chatterley.service.common.IdGenerator
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class SignUpService(
    private val userDao: UserDao,
    private val atomicDbOperation: AtomicDbOperation,
    private val passwordHashService: PasswordHashService,
    private val idGenerator: IdGenerator,
    private val jwtService: JwtService
) {

    suspend fun signUp(nickname: String, password: String): AuthTokens {
        val userOpt = atomicDbOperation.replicaTransactionWithOptResult {
            userDao.findByNickname(nickname)
        }
        if (userOpt != null) {
            throw RuntimeException("User $nickname already exists")
        }

        if (password.length < 8 || password.all { !it.isDigit() } || password.all { !it.isLetter() }) {
            throw RuntimeException("Password must be more complicated")
        }

        val newUser = User(
            id = idGenerator.generateUuid(),
            nickname = nickname,
            passwordHash = passwordHashService.evaluatePasswordHash(password),
            createTime = Instant.now()
        )

        atomicDbOperation.masterTransaction {
            userDao.insert(newUser)
        }

        val userAuthData = UserAuthData(newUser.id, newUser.nickname)
        val jwtAccessToken = jwtService.encodeJwtToken(userAuthData, JwtTokenType.ACCESS_TOKEN)
        val jwtRefreshToken = jwtService.encodeJwtToken(userAuthData, JwtTokenType.REFRESH_TOKEN)
        return AuthTokens(jwtAccessToken, jwtRefreshToken)
    }
}
