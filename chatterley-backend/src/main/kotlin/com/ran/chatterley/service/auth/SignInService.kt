package com.ran.chatterley.service.auth

import com.ran.chatterley.db.component.AtomicDbOperation
import com.ran.chatterley.db.dao.UserDao
import com.ran.chatterley.rest.model.auth.AuthTokens
import com.ran.chatterley.service.auth.model.JwtTokenType
import com.ran.chatterley.service.auth.model.UserAuthData
import org.springframework.stereotype.Service

@Service
class SignInService(
    private val userDao: UserDao,
    private val atomicDbOperation: AtomicDbOperation,
    private val passwordHashService: PasswordHashService,
    private val jwtService: JwtService
) {

    suspend fun signIn(nickname: String, password: String): AuthTokens {
        val user = atomicDbOperation.replicaTransactionWithOptResult {
            userDao.findByNickname(nickname)
        } ?: throw RuntimeException("User $nickname not found") // todo replace with correct exception

        val givenPasswordHash = passwordHashService.evaluatePasswordHash(password)
        if (givenPasswordHash != user.passwordHash) {
            throw RuntimeException("Incorrect password") // todo: replace with correct exception
        }

        val userAuthData = UserAuthData(user.id, user.nickname)
        val jwtAccessToken = jwtService.encodeJwtToken(userAuthData, JwtTokenType.ACCESS_TOKEN)
        val jwtRefreshToken = jwtService.encodeJwtToken(userAuthData, JwtTokenType.REFRESH_TOKEN)
        return AuthTokens(jwtAccessToken, jwtRefreshToken)
    }
}
