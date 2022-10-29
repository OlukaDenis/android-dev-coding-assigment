package com.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class LocalPost(
    @PrimaryKey
    val id: Long,
    val body: String,
    val title: String,
    val userId: Long
)