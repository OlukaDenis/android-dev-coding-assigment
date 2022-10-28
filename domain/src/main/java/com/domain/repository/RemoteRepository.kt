package com.domain.repository

import com.domain.model.*
import okhttp3.RequestBody

interface RemoteRepository {

    suspend fun createUser(data: HashMap<String, String>): UserEntity

    suspend fun editUser(data: RequestBody): UserEntity

    suspend fun fetchSingleUser(userId: Long): UserEntity
}