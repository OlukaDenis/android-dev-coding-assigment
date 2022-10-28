package com.data.remote.services

import com.data.remote.model.RemoteUser
import retrofit2.http.*

interface AuthService {

    @POST("users")
    @FormUrlEncoded
    suspend fun saveUser(@FieldMap params: HashMap<String, String>): RemoteUser

    @GET("users/{id}")
    suspend fun getSingleUser(@Path("id") userId: Long): RemoteUser
}