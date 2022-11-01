package com.data.utils

import com.data.remote.services.ApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun retrofitTest(baseUrl: String) =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

fun apiServiceTest(baseUrl: String): ApiService =
    retrofitTest(baseUrl).create(ApiService::class.java)