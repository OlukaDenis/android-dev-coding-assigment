package com.ensibuuko.android_dev_coding_assigment.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ensibuuko.android_dev_coding_assigment.R
import com.ensibuuko.android_dev_coding_assigment.utils.ProgressUtils
import com.ensibuuko.android_dev_coding_assigment.utils.ToastUtils
import com.ensibuuko.android_dev_coding_assigment.utils.ToastUtils.showAlert

abstract class BaseActivity: AppCompatActivity() {

    private lateinit var progressUtils: ProgressUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressUtils = ProgressUtils(this)
    }

    protected fun showProgressDialog() {
        progressUtils.showDialog()
    }

    protected fun hideProgressDialog() {
        progressUtils.hideDialog()
    }

    fun showErrorAlert(message: String) {
        showAlert(getString(R.string.error), message, ToastUtils.TYPE.FAILURE)
    }

    fun showInfoAlert(message: String) {
        showAlert(getString(R.string.info), message, ToastUtils.TYPE.INFO)
    }

    fun showSuccessAlert(message: String) {
        showAlert(getString(R.string.success), message, ToastUtils.TYPE.SUCCESS)
    }

    override fun onDestroy() {
        super.onDestroy()
        progressUtils.destroyDialog()
    }

}