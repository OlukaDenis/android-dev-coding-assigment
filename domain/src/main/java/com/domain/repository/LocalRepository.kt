package com.domain.repository

import com.domain.model.*
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun saveUser(user: UserEntity)

    suspend fun clearUsers()

    suspend fun getUser(): UserEntity?

    fun getUserById(userId: Long): Flow<UserEntity?>

    suspend fun updateUser(user: UserEntity)


    suspend fun insertPost(entity: PostEntity)

    suspend fun clearPosts()

    suspend fun deletePostById(postId: Long)

    suspend fun updatePost(post: PostEntity)

    fun getPostsByUserId(id: Long): Flow<List<PostEntity>>

    fun getPosts(): Flow<List<PostEntity>>


    suspend fun insertComment(entity: CommentEntity)

    suspend fun clearComments()

    suspend fun deleteCommentById(id: Long)

    fun getCommentsByPostId(postId: Long): Flow<List<CommentEntity>>

    suspend fun updateComment(entity: CommentEntity)

   }