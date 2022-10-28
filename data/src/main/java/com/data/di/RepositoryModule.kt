package com.data.di

import com.data.local.impl.LocalRepositoryImpl
import com.data.local.impl.PreferenceRepositoryImpl
import com.data.remote.impl.AuthRepositoryImpl
import com.data.remote.impl.RemoteRepositoryImpl
import com.data.remote.impl.UtilRepositoryImpl
import com.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindRepository(impl: RemoteRepositoryImpl): RemoteRepository

    @Singleton
    @Binds
    fun bindLocalRepository(impl: LocalRepositoryImpl): LocalRepository

    @Singleton
    @Binds
    fun bindUtilRepository(impl: UtilRepositoryImpl): UtilRepository

    @Singleton
    @Binds
    fun bindPreferenceRepository(impl: PreferenceRepositoryImpl): PreferenceRepository

    @Singleton
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}