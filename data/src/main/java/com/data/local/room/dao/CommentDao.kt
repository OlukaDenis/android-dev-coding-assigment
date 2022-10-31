package com.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.data.base.BaseDao
import com.data.local.model.LocalComment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao : BaseDao<LocalComment> {
    @Query("SELECT * FROM comments ORDER BY updatedAt DESC")
    fun get(): Flow<List<LocalComment>>

    @Query("SELECT * FROM comments WHERE id = :id")
    fun getById(id: Long): LocalComment

    @Query("SELECT * FROM comments WHERE postId = :id ORDER BY updatedAt ASC")
    fun getByPostId(id: Long): Flow<List<LocalComment>>

    @Query("DELETE FROM comments WHERE id == :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM comments")
    suspend fun clear()
}