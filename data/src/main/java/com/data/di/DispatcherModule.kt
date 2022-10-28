package com.data.di

import com.domain.dispacher.AppDispatcher
import com.domain.impl.AppDisptacherImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {

    @Binds
    @Singleton
    internal abstract fun bindDispatcher(impl: AppDisptacherImpl): AppDispatcher
}