package com.domain.utils

import com.domain.model.CommentEntity
import com.domain.model.PostEntity
import com.domain.model.UserEntity

fun getDummyUsers(): List<UserEntity> {
    val users = mutableListOf<UserEntity>()
    ('a'..'z').forEachIndexed { index, c ->
        users.add(
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

val dummyUser = UserEntity(10,"Test", "test@gmail.com", "90898172", "1234", "")

val dummyPost = PostEntity(12, "testing body", "Testing", 1, null, "", "")

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

fun getDummyComments(): List<CommentEntity> {
    val posts = mutableListOf<CommentEntity>()
    ('a'..'z').forEachIndexed { index, c ->
        posts.add(
            CommentEntity(
                id = index.toLong(),
                name = c.toString(),
                email = c.toString(),
                body = c.toString(),
                postId = index.toLong(),
                createdAt = c.toString(),
                updatedAt = c.toString(),
            )
        )
    }
    posts.shuffle()

    return posts
}