package com.data.local.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.data.UserPreferences
import com.data.local.localMappers.UserPreferenceMapper
import com.data.local.utils.*
import com.domain.model.AppPreference
import com.domain.model.UserEntity
import com.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(
    private val datastorePreference: DataStore<Preferences>,
    private val userPreferences: DataStore<UserPreferences>,
    private val userPrefMapper: UserPreferenceMapper
) : PreferenceRepository {

    override fun getAppPreferences(): Flow<AppPreference> = datastorePreference.data.map {
        val dark = it[IS_DARK_MODE] ?: false
        val sysDefault = it[IS_SYSTEM_DEFAULT] ?: false
        AppPreference(dark, sysDefault)
    }

    override suspend fun updateDarkModeTheme(isDarkMode: Boolean) {
        datastorePreference.edit {
            it[IS_DARK_MODE] = isDarkMode
        }
    }

    override suspend fun updateSystemDefaultTheme(isDefaultTheme: Boolean) {
        datastorePreference.edit {
            it[IS_SYSTEM_DEFAULT] = isDefaultTheme
        }
    }

    override suspend fun saveUser(user: UserEntity) {
        userPreferences.updateData { prefs ->
            prefs.toBuilder()
                .setId(user.id)
                .setEmail(user.email)
                .setUsername(user.username)
                .setWebsite(user.website)
                .setPhone(user.phone)
                .build()
        }
    }

    override fun getUser(): Flow<UserEntity> {
        return try {
            userPreferences.data.map { userPrefMapper.toDomain(it) }
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            throw throwable
        }
    }
}