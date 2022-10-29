package com.data.local.di

import android.content.Context
import androidx.room.Room
import com.data.local.room.AppDatabase
import com.data.local.utils.LocalConstants.APP_DATABASE_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        APP_DATABASE_DB
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    internal fun provideUserDao(database: AppDatabase) = database.userDao()


    @Singleton
    @Provides
    internal fun providePostDao(database: AppDatabase) = database.postDao()
}