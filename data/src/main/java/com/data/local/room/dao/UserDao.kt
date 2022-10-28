package com.data.local.room.dao

import androidx.room.*
import com.data.base.BaseDao
import com.data.local.model.LocalUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao: BaseDao<LocalUser> {

    @Query("SELECT * FROM users LIMIT 1")
    fun getTopUser(): List<LocalUser>

    @Query("SELECT * FROM users")
    fun get(): Flow<List<LocalUser>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getById(id: Long): LocalUser

    @Query("DELETE FROM users")
    fun clear()
}