package com.ran.chatterley.rest.controller

import com.ran.chatterley.rest.model.user.User
import com.ran.chatterley.rest.model.user.UserResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController {

    @GetMapping("/me")
    suspend fun getUser(): UserResponse {
        // todo
        return UserResponse(User("jaksdffj", "nicky"))
    }
}
