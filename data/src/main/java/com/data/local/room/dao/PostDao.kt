package com.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.data.base.BaseDao
import com.data.local.model.LocalPost
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao: BaseDao<LocalPost> {
    @Query("SELECT * FROM posts ORDER BY updatedAt DESC")
    fun get(): Flow<List<LocalPost>>

    @Query("SELECT * FROM posts WHERE userId = :id ORDER BY updatedAt DESC")
    fun getPostsByUserId(id: Long): Flow<List<LocalPost>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getById(id: Long): LocalPost

    @Query("DELETE FROM posts WHERE id == :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM posts")
    suspend fun clear()
}