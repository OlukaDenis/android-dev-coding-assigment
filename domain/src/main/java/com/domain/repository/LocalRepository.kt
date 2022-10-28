package com.domain.repository

import com.domain.model.*

interface LocalRepository {

    suspend fun saveUser(user: UserEntity)

    suspend fun clearUsers()

    suspend fun getUser(): UserEntity?

    suspend fun updateUser(user: UserEntity)

   }