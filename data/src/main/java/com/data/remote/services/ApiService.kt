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

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") postId: Long)

    @PATCH("posts/{id}")
    suspend fun updatePost(
        @Path("id") postId: Long,
        @Body request: HashMap<String, Any>
    ): RemotePost

    @GET("posts/{id}")
    suspend fun fetchPostById(@Path("id") postId: Long): RemotePost


    @POST("comments")
    suspend fun createComment(@Body request: HashMap<String, Any>): RemoteComment

    @DELETE("comments/{id}")
    suspend fun deleteComment(@Path("id") id: Long)

    @PATCH("comments/{id}")
    suspend fun updateComment(
        @Path("id") id: Long,
        @Body request: HashMap<String, Any>
    ): RemoteComment

    @GET("posts/{id}/comments")
    suspend fun fetchPostComments(@Path("id") id: Long): List<RemoteComment>

}