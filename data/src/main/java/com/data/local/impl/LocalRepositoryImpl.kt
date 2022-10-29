package com.data.local.impl

import com.data.local.localMappers.*
import com.data.local.room.dao.*
import com.domain.model.*
import com.domain.repository.LocalRepository
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val postDao: PostDao,
    private val localPostMapper: LocalPostMapper,
    private val localUserMapper: LocalUserMapper
) : LocalRepository {

    override suspend fun saveUser(user: UserEntity) {
        userDao.insert(localUserMapper.toLocal(user))
    }

    override suspend fun clearUsers() = userDao.clear()

    override suspend fun getUser(): UserEntity? {
        val list = userDao.getTopUser()
        return if (list.isEmpty()) null else localUserMapper.toDomain(list.first())
    }

    override fun getUserById(userId: Long): Flow<UserEntity?> {
        return userDao.getById(userId).map {
            if (it == null) null else localUserMapper.toDomain(it)
        }
    }

    override suspend fun updateUser(user: UserEntity) {
        userDao.update(localUserMapper.toLocal(user))
    }

    override suspend fun insertPost(post: PostEntity) {
        postDao.insert(localPostMapper.toLocal(post))
    }

    override suspend fun clearPosts() {
        postDao.clear()
    }

    override suspend fun updatePost(post: PostEntity) {
        postDao.update(localPostMapper.toLocal(post))
    }

    override fun getPosts(): Flow<List<PostEntity>>{
         return postDao.get().map { list ->
            list.map {
                localPostMapper.toDomain(it)
            }
         }
    }

}