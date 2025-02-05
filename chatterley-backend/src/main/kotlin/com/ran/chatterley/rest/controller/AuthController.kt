package com.ran.chatterley.rest.controller

import com.ran.chatterley.rest.model.auth.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController {

    @PostMapping("/sign-in")
    suspend fun signIn(@RequestBody request: SignInRequest): SignInResponse {
        // todo
        return SignInResponse(AuthTokens("1234", "4321"))
    }

    @PostMapping("/sign-up")
    suspend fun signUp(@RequestBody request: SignUpRequest): SignUpResponse {
        // todo
        return SignUpResponse(AuthTokens("1234", "4321"))
    }

    @PostMapping("/exchange-tokens")
    suspend fun exchangeTokens(@RequestBody request: ExchangeTokensRequest): ExchangeTokensResponse {
        // todo
        return ExchangeTokensResponse(AuthTokens("1234", "4321"))
    }

    @PostMapping("/change-password")
    suspend fun changePassword(@RequestBody request: ChangePasswordRequest) {
        // todo
    }
}
