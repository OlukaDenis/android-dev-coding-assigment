package com.domain.repository

import com.domain.model.*
import okhttp3.RequestBody

interface RemoteRepository {

    suspend fun createUser(data: HashMap<String, Any>): UserEntity

    suspend fun fetchSingleUser(userId: Long): UserEntity

    suspend fun createPost(data: HashMap<String, Any>): PostEntity

    suspend fun updatePost(postId: Long, data: HashMap<String, Any>): PostEntity

    suspend fun deletePost(postId: Long)

    suspend fun fetchPosts(): List<PostEntity>

    suspend fun createComment(request: HashMap<String, Any>): CommentEntity

    suspend fun fetchPostComments(postId: Long): List<CommentEntity>

    suspend fun updateComment(commentId: Long, data: HashMap<String, Any>): CommentEntity

    suspend fun deleteComment(commentId: Long)
}