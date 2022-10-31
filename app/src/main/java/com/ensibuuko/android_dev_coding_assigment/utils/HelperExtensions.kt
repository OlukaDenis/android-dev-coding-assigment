package com.ensibuuko.android_dev_coding_assigment.utils

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*


fun EditText.removeErrorWatcher(il: TextInputLayout) {
    this.doOnTextChanged { text, _, _, _ ->
        if (text.toString().isNotEmpty() && il.error.toString().isNotEmpty()) {
            il.error = null
            il.isErrorEnabled = false
        }
    }
}