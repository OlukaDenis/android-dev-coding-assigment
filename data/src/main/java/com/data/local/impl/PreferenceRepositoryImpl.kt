package com.data.local.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.data.local.utils.*
import com.domain.model.AppPreference
import com.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(
    private val datastorePreference: DataStore<Preferences>,
): PreferenceRepository {

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
}