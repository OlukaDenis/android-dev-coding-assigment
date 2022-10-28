package com.data.remote.impl

import com.data.remote.remoteMappers.*
import com.data.remote.services.ApiService
import com.data.remote.services.AuthService
import com.domain.dispacher.AppDispatcher
import com.domain.model.*
import com.domain.repository.LocalRepository
import com.domain.repository.PreferenceRepository
import com.domain.repository.RemoteRepository
import okhttp3.RequestBody
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val dispatcher: AppDispatcher,
    private val apiService: ApiService,
    private val authService: AuthService,
    private val local: LocalRepository,
    private val preferences: PreferenceRepository,
    private val remoteUserMapper: RemoteUserMapper
) : RemoteRepository {

    override suspend fun createUser(data: HashMap<String, String>): UserEntity {
        return try {
            val response = authService.saveUser(data)
            remoteUserMapper.mapToDomain(response)
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    override suspend fun fetchSingleUser(userId: Long): UserEntity {
        return try {
            val response = authService.getSingleUser(userId)

            val user = remoteUserMapper.mapToDomain(response)
            local.updateUser(user)

            user
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    override suspend fun editUser(data: RequestBody): UserEntity {
        return try {
            val response = apiService.editUser(data)
            val user = remoteUserMapper.mapToDomain(response)

            fetchSingleUser(user.id)
            user
        } catch (throwable: Throwable) {
            throw throwable
        }
    }
}

