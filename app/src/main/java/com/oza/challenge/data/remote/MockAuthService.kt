package com.oza.challenge.data.remote

import com.oza.challenge.model.AuthResponse
import com.oza.challenge.model.LoginRequest
import com.oza.challenge.model.User
import javax.inject.Singleton

@Singleton
class MockAuthService : AuthService {

    override suspend fun login(loginRequest: LoginRequest): AuthResponse {
        return AuthResponse("01", loginRequest.email, "dummyToken")
    }

    override suspend fun register(user: User): AuthResponse {
        return AuthResponse("01", user.email, "dummyToken")
    }

}
