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

    override suspend fun saveUser(user: UserEntity) {
        userPreferences.updateData { prefs ->
            prefs.toBuilder()
                .setId(user.id)
                .setName(user.name)
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

    override suspend fun savePassword(input: String) {
        datastorePreference.edit {
            it[USER_PASSWORD] = input
        }
    }

    override fun getPassword(): Flow<String> = datastorePreference.data.map {
        it[USER_PASSWORD] ?: ""
    }
}