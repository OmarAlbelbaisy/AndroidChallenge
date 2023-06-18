package com.oza.challenge.ui.register

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
import com.oza.challenge.databinding.FragmentRegisterBinding
import com.oza.challenge.model.LoginRequest
import com.oza.challenge.model.User
import com.oza.challenge.ui.MainActivity
import com.oza.challenge.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    @Inject
    lateinit var cache: AppCache

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val age = binding.ageEditText.text.toString()
            viewModel.user.value = User(email, password, age)
            viewModel.register()
        }

        viewModel.registerResponse.observe(viewLifecycleOwner) {
            cache.saveToken(it.token)
            findNavController().navigate(R.id.action_registrationFragment_to_homeFragment)
        }

        viewModel.registerErrorResponse.observe(viewLifecycleOwner) {
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

        viewModel.ageError.observe(viewLifecycleOwner) {
            binding.ageEditText.error = if (it) {
                getString(R.string.error_age)
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
