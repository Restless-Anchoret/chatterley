package com.ran.chatterley.rest.controller

import com.ran.chatterley.rest.model.user.User
import com.ran.chatterley.rest.model.user.UserResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/api/v1/user")
    fun getUser(): UserResponse {
        return UserResponse(User("jaksdffj", "nicky"))
    }
}
