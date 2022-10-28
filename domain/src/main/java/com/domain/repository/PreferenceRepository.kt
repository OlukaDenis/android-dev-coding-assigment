package com.domain.repository

import com.domain.model.AppPreference
import com.domain.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {

    fun getAppPreferences(): Flow<AppPreference>

    suspend fun updateDarkModeTheme(isDarkMode: Boolean)

    suspend fun updateSystemDefaultTheme(isDefaultTheme: Boolean)

    suspend fun saveUser(user: UserEntity)

    fun getUser(): Flow<UserEntity>
}