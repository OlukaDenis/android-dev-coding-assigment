package com.data.local.impl

import com.data.local.localMappers.*
import com.data.local.room.dao.*
import com.domain.model.*
import com.domain.repository.LocalRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val localUserMapper: LocalUserMapper
) : LocalRepository {

    override suspend fun saveUserToDb(user: UserEntity) {
        userDao.insert(localUserMapper.toLocal(user))
    }

    override suspend fun clearUsers() = userDao.clear()

    override suspend fun getUser(): UserEntity? {
        val list = userDao.getTopUser()
        return if (list.isEmpty()) null else localUserMapper.toDomain(list.first())
    }

    override suspend fun updateUser(user: UserEntity) {
        userDao.update(localUserMapper.toLocal(user))
    }

}