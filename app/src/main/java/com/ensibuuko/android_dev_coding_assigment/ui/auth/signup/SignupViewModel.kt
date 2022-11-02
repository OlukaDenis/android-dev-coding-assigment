package com.ensibuuko.android_dev_coding_assigment.ui.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.model.sealed.Resource
import com.domain.usecases.users.CreateUserUseCase
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseUiSate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
) : ViewModel() {

    private val _signupState = MutableLiveData<BaseUiSate>()
    val signupState get() = _signupState

    fun createUser(
        name: String,
        email: String,
        phone: String,
        password: String
    ) {
        viewModelScope.launch {
            val param = CreateUserUseCase.Param(
                name = name,
                phone = phone,
                email = email,
                password = password
            )
            createUserUseCase(param).collect {
                when(it) {
                    is Resource.Loading -> _signupState.value = BaseUiSate.Loading
                    is Resource.Success -> _signupState.value = BaseUiSate.Success(it.data)
                    is Resource.Error -> _signupState.value = BaseUiSate.Error(it.exception)
                }
            }
        }
    }
}