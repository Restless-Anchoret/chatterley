package com.ran.chatterley.rest.controller

import com.ran.chatterley.rest.model.auth.*
import com.ran.chatterley.service.auth.SignInService
import com.ran.chatterley.service.auth.SignUpService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val signInService: SignInService,
    private val signUpService: SignUpService
) {

    @PostMapping("/sign-in")
    suspend fun signIn(@RequestBody request: SignInRequest): SignInResponse {
        val authTokens = signInService.signIn(request.nickname, request.password)
        return SignInResponse(authTokens)
    }

    @PostMapping("/sign-up")
    suspend fun signUp(@RequestBody request: SignUpRequest): SignUpResponse {
        val authTokens = signUpService.signUp(request.nickname, request.password)
        return SignUpResponse(authTokens)
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
