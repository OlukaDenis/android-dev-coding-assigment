package com.ensibuuko.android_dev_coding_assigment.ui.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.dispacher.AppDispatcher
import com.domain.model.UserEntity
import com.domain.usecases.users.GetSavedPasswordUseCase
import com.domain.usecases.users.GetSavedUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getSavedUserUseCase: GetSavedUserUseCase,
    private val getSavedPasswordUseCase: GetSavedPasswordUseCase,
    private val dispatcher: AppDispatcher
) : ViewModel() {

    private var _userState = MutableLiveData<UserEntity>()
    val userState get() = _userState

    val savedPassword = runBlocking { getSavedPasswordUseCase().first() }

    fun getSavedUser() {
        viewModelScope.launch(dispatcher.main) {
            getSavedUserUseCase().collect {
                _userState.value = it
            }
        }
    }
}