package com.ensibuuko.android_dev_coding_assigment.ui.auth.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.databinding.FragmentLoginBinding
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSavedUser()
        observeSavedUser()

        binding.mtvRegister.setOnClickListener {
            navigate(
                LoginFragmentDirections.actionLoginFragmentToSignupFragment()
            )
        }
    }

    private fun observeSavedUser() {
        viewModel.userState.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                Timber.e("No saved user")
            } else {
                Timber.d("User got: $it")
            }
        }
    }
}