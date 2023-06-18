package com.oza.challenge.ui.register

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation.findNavController
import com.oza.challenge.R
import com.oza.challenge.model.AuthResponse
import com.oza.challenge.model.User
import com.oza.challenge.repository.AuthRepository
import com.oza.challenge.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.NumberFormatException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    val user = MutableLiveData<User>()
    val emailError = MutableLiveData<Boolean>()
    val passwordError = MutableLiveData<Boolean>()
    val ageError = MutableLiveData<Boolean>()

    private val _registerResponse = MutableLiveData<AuthResponse>()
    val registerResponse: LiveData<AuthResponse> = _registerResponse

    private val _registerErrorResponse = MutableLiveData<String>()
    val registerErrorResponse: LiveData<String> = _registerErrorResponse

    fun register() {
        val user = user.value ?: return
        emailError.value = false
        passwordError.value = false
        ageError.value = false
        // Perform validation on the user input here before calling the repository
        // Validate email and password
        if (!isEmailValid(user.email)) {
            emailError.value = true
        } else if (!isPasswordValid(user.password)) {
            passwordError.value = true
        } else if(!isAgeValid(user.age)) {
            ageError.value = true
        }else {
            // Call the login method in the repository and update the login result LiveData accordingly
            handleResource(
                { authRepository.register(user) },
                _registerResponse,
                _registerErrorResponse,
                "register"
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

    private fun isAgeValid(age: String): Boolean {
        return try {
            age.toInt() in 18..99
        } catch (e: NumberFormatException) {
            false
        }
    }

}
