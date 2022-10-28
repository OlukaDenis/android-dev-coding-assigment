package com.domain.repository

interface UtilRepository {

    fun getNetworkError(throwable: Throwable): String
}