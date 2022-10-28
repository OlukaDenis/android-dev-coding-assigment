package com.data.remote.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.data.qualifiers.ApiQualifier
import com.data.qualifiers.AuthQualifier
import com.makao.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    internal fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    @ApiQualifier
    internal fun provideApiRetrofit(
        @ApiQualifier client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        val builder = Retrofit.Builder()
        builder.apply {
            baseUrl(BuildConfig.BASE_URL)
            client(client)
            addConverterFactory(converterFactory)
        }
        return builder.build()
    }


    @Provides
    @Singleton
    @AuthQualifier
    internal fun provideAuthRetrofit(
        @AuthQualifier client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        val builder = Retrofit.Builder()
        builder.apply {
            baseUrl(BuildConfig.BASE_URL)
            client(client)
            addConverterFactory(converterFactory)
        }
        return builder.build()
    }
}