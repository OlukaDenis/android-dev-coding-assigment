package com.data.remote.services

import com.data.remote.model.*
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("user/edit")
    suspend fun editUser(@Body data: RequestBody): RemoteUser

}