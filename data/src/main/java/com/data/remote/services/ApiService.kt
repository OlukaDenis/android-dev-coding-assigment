package com.data.remote.services

import com.data.remote.model.*
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @GET("users")
    suspend fun fetchUsers(): List<RemoteUser>

    @POST("users")
    suspend fun createUser(@Body request: HashMap<String, Any>): RemoteUser

    @GET("users/{id}")
    suspend fun fetchUserById(@Path("id") userId: Long): RemoteUser

    @GET("posts")
    suspend fun fetchPosts(): List<RemotePost>

    @POST("posts")
    suspend fun createPost(@Body request: HashMap<String, Any>): RemotePost

    @GET("posts/{id}")
    suspend fun fetchPostById(@Path("id") postId: Long): RemotePost

}