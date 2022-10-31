package com.domain.repository

import com.domain.model.*
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun saveUser(user: UserEntity)

    suspend fun clearUsers()

    suspend fun getUser(): UserEntity?

    fun getUserById(userId: Long): Flow<UserEntity?>

    suspend fun updateUser(user: UserEntity)

    suspend fun insertPost(post: PostEntity)

    suspend fun clearPosts()

    suspend fun deletePostById(postId: Long)

    suspend fun updatePost(post: PostEntity)

    fun getPosts(): Flow<List<PostEntity>>

   }