package com.oza.challenge.ui.login

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation.findNavController
import com.oza.challenge.R
import com.oza.challenge.model.AuthResponse
import com.oza.challenge.model.LoginRequest
import com.oza.challenge.repository.AuthRepository
import com.oza.challenge.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    val loginRequest = MutableLiveData<LoginRequest>()
    val emailError = MutableLiveData<Boolean>()
    val passwordError = MutableLiveData<Boolean>()

    private val _loginResult = MutableLiveData<AuthResponse>()
    val loginResult: LiveData<AuthResponse> = _loginResult

    private val _loginErrorResult = MutableLiveData<String>()
    val loginErrorResult: LiveData<String> = _loginErrorResult

    fun login() {
        val loginRequest = loginRequest.value ?: return
        emailError.value = false
        passwordError.value = false
        // Perform validation on the user input here before calling the repository
        // Validate email and password
        if (!isEmailValid(loginRequest.email)) {
            emailError.value = true
        } else if (!isPasswordValid(loginRequest.password)) {
            passwordError.value = true
        } else {
            // Call the login method in the repository and update the login result LiveData accordingly
            handleResource(
                { authRepository.login(loginRequest) },
                _loginResult,
                _loginErrorResult,
                "login"
            )
        }
    }

    fun navigateToRegistration(view: View) {
        // Navigate to the registration page using the Navigation component
        val navController = findNavController(view)
        navController.navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    private fun isEmailValid(email: String): Boolean {
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        return email.matches(emailPattern)
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = Regex("^.{6,12}$")
        return password.matches(passwordPattern)
    }

}
