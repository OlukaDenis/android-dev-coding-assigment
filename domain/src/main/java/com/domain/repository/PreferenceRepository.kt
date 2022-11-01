package com.domain.repository

import com.domain.model.AppPreference
import com.domain.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {

    suspend fun saveUser(user: UserEntity)

    fun getUser(): Flow<UserEntity>

    suspend fun savePassword(input: String)

    fun getPassword(): Flow<String>
}