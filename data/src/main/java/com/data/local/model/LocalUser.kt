package com.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class LocalUser(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val email: String,
    val username: String,
    val phone: String,
    val website: String
)