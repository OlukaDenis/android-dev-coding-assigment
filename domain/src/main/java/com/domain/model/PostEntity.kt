package com.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostEntity(
    var id: Long,
    var body: String,
    var title: String,
    var userId: Long,
    var user: UserEntity?,
    var updatedAt: String,
    var createdAt: String
) : Parcelable