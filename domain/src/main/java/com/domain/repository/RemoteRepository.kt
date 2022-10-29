package com.domain.repository

import com.domain.model.*
import okhttp3.RequestBody

interface RemoteRepository {

    suspend fun createUser(data: HashMap<String, Any>): UserEntity

    suspend fun fetchSingleUser(userId: Long): UserEntity

    suspend fun fetchPosts(): List<PostEntity>
}