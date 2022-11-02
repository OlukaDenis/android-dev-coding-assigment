package com.ensibuuko.android_dev_coding_assigment.ui.base

sealed class BaseUiSate {
    object Loading: BaseUiSate()
    data class Error(val message: String): BaseUiSate()
    data class Success<T>(val data: T): BaseUiSate()
}