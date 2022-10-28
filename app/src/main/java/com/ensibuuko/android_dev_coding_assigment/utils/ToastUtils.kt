package com.ensibuuko.android_dev_coding_assigment.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import com.ensibuuko.android_dev_coding_assigment.R
import com.tapadoo.alerter.Alerter

object ToastUtils {

    const val DURATION_SHORT = 500L
    const val DURATION_NORMAL = 1500L
    const val DURATION_LONG = 2000L

    enum class TYPE { INFO, FAILURE, SUCCESS }

    fun <F : Fragment> F.showAlert(
        title: String,
        description: String,
        type: TYPE,
        duration: Long = DURATION_NORMAL
    ) {
//        val icon = when (type) {
//            TYPE.INFO -> R.drawable.ic_alert_info
//            TYPE.FAILURE -> R.drawable.ic_alert_fail
//            TYPE.SUCCESS -> R.drawable.ic_alert_success
//        }
        val backgroundColor = when (type) {
            TYPE.INFO -> R.color.blue_ocean
            TYPE.FAILURE -> R.color.danger
            TYPE.SUCCESS -> R.color.success
        }
        Alerter.create(requireActivity())
            .setTitle(title)
            .setText(description)
//            .setIcon(icon)
            .setTextAppearance(R.style.AlerterStyle)
            .setTitleAppearance(R.style.AlerterStyle)
            .setBackgroundColorRes(backgroundColor)
            .setDuration(duration)
            .enableIconPulse(true)
            .enableSwipeToDismiss()
            .show()
    }

    fun <A : Activity> A.showAlert(
        title: String,
        description: String,
        type: TYPE,
        duration: Long = DURATION_NORMAL
    ) {
//        val icon = when (type) {
//            TYPE.INFO -> R.drawable.ic_alert_info
//            TYPE.FAILURE -> R.drawable.ic_alert_fail
//            TYPE.SUCCESS -> R.drawable.ic_alert_success
//        }
        val backgroundColor = when (type) {
            TYPE.INFO -> R.color.blue_ocean
            TYPE.FAILURE -> R.color.danger
            TYPE.SUCCESS -> R.color.success
        }
        Alerter.create(this)
            .setTitle(title)
            .setText(description)
//            .setIcon(icon)
            .setTextAppearance(R.style.AlerterStyle)
            .setTitleAppearance(R.style.AlerterStyle)
            .setBackgroundColorRes(backgroundColor)
            .setDuration(duration)
            .enableIconPulse(true)
            .enableSwipeToDismiss()
            .show()
    }
}