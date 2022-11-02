package com.ensibuuko.android_dev_coding_assigment.utils

import android.util.Patterns

object Validator {

    fun isValidEmail(input: String): Boolean =
        input.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(input).matches()

    fun isValidPassword(input: String): Boolean = input.isNotEmpty() &&
            input.length >= 4
//            input.any { it.isUpperCase() } &&
//            input.any { it.isDigit() } &&
//            input.any { it.isLowerCase() }

    fun isValidPhone(input: String): Boolean = AppConstants.PHONE_REGEX.matches(input)

    fun isRequired(input: String): Boolean = input.isNotEmpty()
}