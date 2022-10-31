package com.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentEntity(
    val body: String,
    val email: String,
    val id: Long,
    val name: String,
    val postId: Long,
    var createdAt: String,
    var updatedAt: String
) : Parcelable