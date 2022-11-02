package com.ensibuuko.android_dev_coding_assigment.ui.auth.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.domain.model.UserEntity
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentSignupBinding
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseFragment
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseUiSate
import com.ensibuuko.android_dev_coding_assigment.utils.ToastUtils.showAlert
import com.ensibuuko.android_dev_coding_assigment.utils.Validator
import com.ensibuuko.android_dev_coding_assigment.utils.removeErrorWatcher
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {

    private val viewModel: SignupViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTextWatchers()

        binding.btnSignup.setOnClickListener { validateFields() }
        binding.mtvLogin.setOnClickListener { navigateUp() }

        observeSignUp()
    }

    private fun observeSignUp() {
        viewModel.signupState.observe(viewLifecycleOwner) {
            when(it) {
                is BaseUiSate.Success<*> -> {
                    hideProgressDialog()
                    val user = it.data as UserEntity
                    Timber.d("User saved: $user")
                    showSuccessAlert("Account created successfully")
                    navigateUp()
                }

                is BaseUiSate.Error -> {
                    hideProgressDialog()
                    showErrorAlert(it.message)
                }

                is BaseUiSate.Loading -> {
                    showProgressDialog()
                }
            }
        }
    }

    private fun addTextWatchers() {
        with(binding) {
            etName.removeErrorWatcher(ilName)
            etEmail.removeErrorWatcher(ilEmail)
            etPhone.removeErrorWatcher(ilPhone)
            etPassword.removeErrorWatcher(ilPassword)
        }
    }

    private fun validateFields() {
        with(binding) {
            when {
                !Validator.isRequired(etName.text.toString()) -> {
                    ilName.error = getString(R.string.name_required)
                }

                !Validator.isValidEmail(etEmail.text.toString()) -> {
                    ilEmail.error = getString(R.string.invalid_email)
                }

                !Validator.isValidPhone(etPhone.text.toString()) -> {
                    ilPhone.error = getString(R.string.invalid_phone)
                }

                !Validator.isValidPassword(etPassword.text.toString()) -> {
                    ilPassword.error = getString(R.string.invalid_password)
                }

                else -> {
                    viewModel.createUser(
                        etName.text.toString(),
                        etEmail.text.toString(),
                        etPhone.text.toString(),
                        etPassword.text.toString()
                    )
                }

            }
        }
    }

}