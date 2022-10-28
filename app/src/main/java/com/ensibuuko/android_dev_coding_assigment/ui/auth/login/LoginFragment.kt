package com.ensibuuko.android_dev_coding_assigment.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ensibuuko.android_dev_coding_assigment.ui.home.MainActivity
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentLoginBinding
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseFragment
import com.ensibuuko.android_dev_coding_assigment.utils.Validator
import com.ensibuuko.android_dev_coding_assigment.utils.removeErrorWatcher
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSavedUser()

        binding.mtvRegister.setOnClickListener {
            navigate(
                LoginFragmentDirections.actionLoginFragmentToSignupFragment()
            )
        }

        binding.btnLogin.setOnClickListener { validateFields() }

        addTextWatchers()
    }

    private fun validateFields() {
        with(binding) {
            when {
                !Validator.isValidEmail(etEmail.text.toString()) -> {
                    ilEmail.error = getString(R.string.invalid_email)
                }

                !Validator.isValidPassword(etPassword.text.toString()) -> {
                    ilPassword.error = getString(R.string.invalid_password)
                }

                else -> {
                    observeSavedUser(etEmail.text.toString(), etPassword.text.toString())
                }
            }
        }
    }

    private fun addTextWatchers() {
        with(binding) {
            etEmail.removeErrorWatcher(ilEmail)
            etPassword.removeErrorWatcher(ilPassword)
        }
    }

    private fun observeSavedUser(email: String, password: String) {
        viewModel.userState.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                Timber.e("No saved user")
            } else {
                Timber.d("Saved Password: ${viewModel.savedPassword}")
                when {
                    email != it.email -> {
                        showErrorAlert("Wrong user email")
                    }

                    password != viewModel.savedPassword -> {
                        showErrorAlert("Wrong password")
                    }

                    else -> {
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                        requireActivity().finish()
                    }
                }
            }
        }
    }
}