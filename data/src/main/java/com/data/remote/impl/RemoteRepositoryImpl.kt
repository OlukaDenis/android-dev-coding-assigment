package com.data.remote.impl

import com.data.remote.remoteMappers.*
import com.data.remote.services.ApiService
import com.domain.dispacher.AppDispatcher
import com.domain.model.*
import com.domain.repository.LocalRepository
import com.domain.repository.PreferenceRepository
import com.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val local: LocalRepository,
    private val remoteUserMapper: RemoteUserMapper,
    private val remotePostMapper: RemotePostMapper,
    private val remoteCommentMapper: RemoteCommentMapper
) : RemoteRepository {

    override suspend fun createUser(data: HashMap<String, Any>): UserEntity {
        return try {
            val response = apiService.createUser(data)
            remoteUserMapper.mapToDomain(response)
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    override suspend fun fetchSingleUser(userId: Long): UserEntity {
        return try {
            val response = apiService.fetchUserById(userId)

            val user = remoteUserMapper.mapToDomain(response)
            local.updateUser(user)

            user
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    override suspend fun createPost(data: HashMap<String, Any>): PostEntity {
        return try {
            val response = apiService.createPost(data)
            remotePostMapper.mapToDomain(response)
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    override suspend fun updatePost(postId: Long, data: HashMap<String, Any>): PostEntity {
        return try {
            val response = apiService.updatePost(postId, data)
            remotePostMapper.mapToDomain(response)
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    override suspend fun deletePost(postId: Long) {
        return try {
            apiService.deletePost(postId)
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    override suspend fun fetchPosts(): List<PostEntity> {
        return try {
            val response = apiService.fetchPosts()
            val result = response.map { remotePostMapper.mapToDomain(it) }

            result
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    override suspend fun createComment(request: HashMap<String, Any>): CommentEntity {
        return try {
            val response = apiService.createComment(request)
            remoteCommentMapper.mapToDomain(response)
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    override suspend fun fetchPostComments(postId: Long): List<CommentEntity> {
        return try {
            val response = apiService.fetchPostComments(postId)
            response.map { remoteCommentMapper.mapToDomain(it) }
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    override suspend fun updateComment(commentId: Long, data: HashMap<String, Any>): CommentEntity {
        return try {
            val response = apiService.updateComment(commentId, data)
            remoteCommentMapper.mapToDomain(response)
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    override suspend fun deleteComment(commentId: Long) {
        return try {
            apiService.deleteComment(commentId)
        } catch (throwable: Throwable) {
            throw throwable
        }
    }
}

