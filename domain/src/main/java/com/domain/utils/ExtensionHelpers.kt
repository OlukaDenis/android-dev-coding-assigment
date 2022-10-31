package com.domain.utils

import java.text.SimpleDateFormat
import java.util.*

fun getDateTime(): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Date().time)
}