package com.ran.chatterley.service.auth

import com.ran.chatterley.service.auth.model.JwtTokenType
import com.ran.chatterley.service.auth.model.UserAuthData
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.HashMap

@Service
class JwtService {

    suspend fun encodeJwtToken(userAuthData: UserAuthData, jwtTokenType: JwtTokenType): String {
        val expirationTypeInDays = when (jwtTokenType) {
            JwtTokenType.ACCESS_TOKEN -> ACCESS_TOKENS_EXPIRATION_TIME_IN_DAYS
            JwtTokenType.REFRESH_TOKEN -> REFRESH_TOKENS_EXPIRATION_TIME_IN_DAYS
        }
        val validUntil = Instant.now().plusSeconds(expirationTypeInDays * SECONDS_IN_DAY)

        val tokenData = HashMap<String, Any>()
        tokenData["ID"] = userAuthData.id
        tokenData["NICKNAME"] = userAuthData.nickname
        tokenData["VALID_UNTIL"] = validUntil.toEpochMilli()
        tokenData["TOKEN_TYPE"] = jwtTokenType.toString()

        // todo: stop using deprecated api here
        val jwtBuilder = Jwts.builder()
        jwtBuilder.setClaims(tokenData)
        return jwtBuilder.signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact()
    }

    suspend fun decodeJwtToken(jwtToken: String, expectedJwtTokenType: JwtTokenType): UserAuthData {
        val tokenClaims = try {
            Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .build()
                .parse(jwtToken).payload as Claims
        } catch (ex: Exception) {
            throw RuntimeException("Invalid JWT token", ex) // todo: use correct exceptions here
        }

        val jwtTokenTypeString = tokenClaims.get("TOKEN_TYPE", String::class.java)
        val jwtTokenType = JwtTokenType.entries
            .find { it.toString() == jwtTokenTypeString }
            ?: throw RuntimeException("Unknown JWT token type") // todo: use correct exceptions here
        if (jwtTokenType != expectedJwtTokenType) {
            throw RuntimeException("Another JWT token type was expected") // todo: use correct exceptions here
        }

        val validUntil = tokenClaims.get("VALID_UNTIL", Long::class.java)
        if (Instant.ofEpochMilli(validUntil).isBefore(Instant.now())) {
            throw RuntimeException("Expired JWT token") // todo: use correct exceptions here
        }

        val userId = tokenClaims.get("ID", String::class.java)
        val nickname = tokenClaims.get("NICKNAME", String::class.java)
        return UserAuthData(userId, nickname)
    }

    companion object {
        private val ACCESS_TOKENS_EXPIRATION_TIME_IN_DAYS = 1
        private val REFRESH_TOKENS_EXPIRATION_TIME_IN_DAYS = 2
        private val SECONDS_IN_DAY: Long = 24 * 60 * 60
        // todo: move jwt secret into config file
        private val JWT_SECRET = "jkajrg88aysgy7a8dfgad7fgagf78aasdf67asdf678f7daaasdf76f7d6a7sdfa6sdf6f6d7sadsf6"
    }
}
