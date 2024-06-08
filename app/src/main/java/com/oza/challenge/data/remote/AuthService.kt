package com.oza.challenge.data.remote

import com.oza.challenge.model.AuthResponse
import com.oza.challenge.model.LoginRequest
import com.oza.challenge.model.User
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface AuthService {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): AuthResponse

    @POST("register")
    suspend fun register(@Body user: User): AuthResponse

}
