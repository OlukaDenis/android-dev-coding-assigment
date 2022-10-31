package com.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.data.local.converters.Converters
import com.data.local.model.*
import com.data.local.room.dao.*
import com.domain.model.*

@TypeConverters(Converters::class)
@Database(
    entities = [
        LocalUser::class,
        LocalPost::class,
        LocalComment::class
    ],
    version = 10,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
}