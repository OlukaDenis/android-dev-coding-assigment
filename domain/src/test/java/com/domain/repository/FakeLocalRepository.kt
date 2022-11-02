package com.domain.repository

import com.domain.model.CommentEntity
import com.domain.model.PostEntity
import com.domain.model.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalRepository: LocalRepository {

    private val users = mutableListOf<UserEntity>()
    private val posts = mutableListOf<PostEntity>()
    private val comments = mutableListOf<CommentEntity>()

    override suspend fun saveUser(user: UserEntity) {
        users.add(user)
    }

    override suspend fun clearUsers() {
        users.clear()
    }

    override suspend fun getUser(): UserEntity? {
        return users.firstOrNull()
    }

    override fun getUserById(userId: Long): Flow<UserEntity?> {
        return flow {
            emit(users.find { it.id == userId })
        }
    }

    override suspend fun updateUser(user: UserEntity) {
        val position = users.indexOf(users.find { it.id == user.id })
        users.add(position, user)
    }

    override suspend fun insertPost(entity: PostEntity) {
        posts.add(entity)
    }

    override suspend fun clearPosts() {
        posts.clear()
    }

    override suspend fun deletePostById(postId: Long) {
        val position = posts.indexOf(posts.find { it.id == postId })
        users.removeAt(position)
    }

    override suspend fun updatePost(post: PostEntity) {
        val position = posts.indexOf(posts.find { it.id == post.id })
        posts.add(position, post)
    }

    override fun getPostsByUserId(id: Long): Flow<List<PostEntity>> {
        return flow {
            emit(posts.filter { it.userId == id })
        }
    }

    override fun getPosts(): Flow<List<PostEntity>> {
        return flow { emit(posts) }
    }

    override suspend fun insertComment(entity: CommentEntity) {
        comments.add(entity)
    }

    override suspend fun clearComments() {
        comments.clear()
    }

    override suspend fun deleteCommentById(id: Long) {
        val position = comments.indexOf(comments.find { it.id == id })
        comments.removeAt(position)
    }

    override fun getCommentsByPostId(postId: Long): Flow<List<CommentEntity>> {
        return flow {
            emit(comments.filter { it.postId == postId })
        }
    }

    override suspend fun updateComment(entity: CommentEntity) {
        val position = comments.indexOf(comments.find { it.id == entity.id })
        comments.add(position, entity)
    }
}