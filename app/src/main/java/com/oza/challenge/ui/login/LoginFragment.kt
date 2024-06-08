package com.oza.challenge.ui.login

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.oza.challenge.R
import com.oza.challenge.data.local.AppCache
import com.oza.challenge.databinding.FragmentLoginBinding
import com.oza.challenge.model.LoginRequest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var cache: AppCache

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.loginRequest.value = LoginRequest(email, password)
            viewModel.login()
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        viewModel.loginResult.observe(viewLifecycleOwner) {
            cache.saveToken(it.token)
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        viewModel.loginErrorResult.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                .setTextColor(Color.RED)
                .show()
        }

        viewModel.emailError.observe(viewLifecycleOwner) {
            binding.emailInputLayout.error = if (it) {
                getString(R.string.error_email)
            } else {
                null
            }
        }

        viewModel.passwordError.observe(viewLifecycleOwner) {
            binding.passwordInputLayout.error = if (it) {
                getString(R.string.error_password)
            } else {
                null
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
