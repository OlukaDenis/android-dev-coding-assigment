package com.domain.model

data class PostEntity(
    val id: Long,
    val body: String,
    val title: String,
    val userId: Long,
    var user: UserEntity?
)