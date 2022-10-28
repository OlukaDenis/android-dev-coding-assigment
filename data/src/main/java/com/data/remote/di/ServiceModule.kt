package com.data.remote.di

import com.data.qualifiers.ApiQualifier
import com.data.qualifiers.AuthQualifier
import com.data.remote.services.AuthService
import com.data.remote.services.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    internal fun provideAuthenticationService(
        @AuthQualifier retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideApiService(
        @ApiQualifier retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}