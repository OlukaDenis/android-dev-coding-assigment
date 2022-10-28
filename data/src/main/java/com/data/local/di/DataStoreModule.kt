package com.data.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.data.local.utils.appThemeDatastore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

//    @Provides
//    @Singleton
//    internal fun provideUSerPreferenceDatastore(
//        @ApplicationContext context: Context
//    ): DataStore<UserPreference> = context.userProtoDataStore

    @Provides
    @Singleton
    internal fun provideSettingsPreference(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.appThemeDatastore

}