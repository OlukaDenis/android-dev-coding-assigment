package com.data.local.utils

import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * A method that extracts and formats the error returned from an endpoint
 */
fun Throwable.resolveError(): String {

    return when (this) {
        is SocketTimeoutException -> {
           "Request timeout, Please try again"
        }
        is ConnectException -> {
            "No internet access"
        }
        is UnknownHostException -> {
            "Unable to reach server"
        }

        is IOException -> {
            "No internet access"
        }

        is HttpException -> {
            try {
                this.response()?.message() ?: "Error"
            } catch (ex: Exception) {
                "Connection failed"
            }
        }

        else -> {
            "Error occurred"
        }
    }
}