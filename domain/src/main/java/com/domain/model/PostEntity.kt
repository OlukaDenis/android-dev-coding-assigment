package com.domain.model

data class PostEntity(
    val body: String,
    val id: Long,
    val title: String,
    val userId: Long
)