package com.oza.challenge.repository

import com.oza.challenge.data.remote.AuthService
import com.oza.challenge.model.LoginRequest
import com.oza.challenge.model.User
import com.oza.challenge.repository.base.BaseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val authService: AuthService) : BaseRepository() {

    suspend fun login(loginRequest: LoginRequest) = apiCall { authService.login(loginRequest) }

    suspend fun register(user: User) = apiCall { authService.register(user) }

}
