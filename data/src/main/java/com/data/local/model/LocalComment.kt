package com.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class LocalComment(
    @PrimaryKey
    val id: Long,
    val body: String,
    val email: String,
    val name: String,
    val postId: Long,
    var createdAt: String,
    var updatedAt: String
)