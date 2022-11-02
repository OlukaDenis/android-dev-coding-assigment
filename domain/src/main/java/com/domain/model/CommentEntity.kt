package com.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentEntity(
    var body: String,
    val email: String,
    val id: Long,
    val name: String,
    val postId: Long,
    var createdAt: String,
    var updatedAt: String
) : Parcelable