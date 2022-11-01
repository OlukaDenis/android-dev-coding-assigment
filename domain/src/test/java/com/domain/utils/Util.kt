package com.domain.utils

import com.domain.model.PostEntity
import com.domain.model.UserEntity

fun getDummyUsers(): List<UserEntity> {
    val users = mutableListOf<UserEntity>()
    ('a'..'z').forEachIndexed { index, c ->
        users.add(
//            PostEntity(
//                id = index.toLong(),
//                title = c.toString(),
//                body = c.toString(),
//                userId = index.toLong(),
//                createdAt = c.toString(),
//                updatedAt = c.toString(),
//                user = null
//            )
            UserEntity(
                id = index.toLong(),
                name = c.toString(),
                email = c.toString(),
                phone = c.toString(),
                website = c.toString(),
                username = c.toString()
            )

        )
    }
    users.shuffle()
    return users
}


fun getDummyPosts(): List<PostEntity> {
    val posts = mutableListOf<PostEntity>()
    ('a'..'z').forEachIndexed { index, c ->
        posts.add(
            PostEntity(
                id = index.toLong(),
                title = c.toString(),
                body = c.toString(),
                userId = index.toLong(),
                createdAt = c.toString(),
                updatedAt = c.toString(),
                user = null
            )
        )
    }
    posts.shuffle()

    return posts
}