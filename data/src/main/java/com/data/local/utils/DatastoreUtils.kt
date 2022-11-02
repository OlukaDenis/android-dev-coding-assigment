package com.data.local.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.data.UserPreferences
import com.data.local.serializers.UserPreferenceSerializer
import com.data.local.utils.LocalConstants.APP_PREFERENCES_NAME

val Context.userProtoDataStore: DataStore<UserPreferences> by dataStore(
    fileName = LocalConstants.USER_PREF_DB,
    serializer = UserPreferenceSerializer
)

val Context.appThemeDatastore by preferencesDataStore(
    name = APP_PREFERENCES_NAME
)

val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")

val IS_SYSTEM_DEFAULT  = booleanPreferencesKey("is_system_default")

val USER_PASSWORD = stringPreferencesKey("user_password")