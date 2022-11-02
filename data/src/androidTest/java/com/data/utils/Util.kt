package com.data.utils

import com.data.local.model.LocalComment
import com.data.local.model.LocalPost
import com.data.local.model.LocalUser
import com.domain.model.CommentEntity
import com.domain.model.PostEntity
import com.domain.model.UserEntity

val dummyUser = LocalUser(10,"Test", "test@gmail.com", "90898172", "1234", "")

val dummyPost = LocalPost(12, "testing body", "Testing", 1, "", "")

fun getDummyUsers(): List<LocalUser> {
    val users = mutableListOf<LocalUser>()
    ('a'..'z').forEachIndexed { index, c ->
        users.add(
            LocalUser(
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
fun getDummyPosts(): List<LocalPost> {
    val posts = mutableListOf<LocalPost>()
    ('a'..'z').forEachIndexed { index, c ->
        posts.add(
            LocalPost(
                id = index.toLong(),
                title = c.toString(),
                body = c.toString(),
                userId = index.toLong(),
                createdAt = c.toString(),
                updatedAt = c.toString()
            )
        )
    }
    posts.shuffle()

    return posts
}

fun getDummyComments(): List<LocalComment> {
    val posts = mutableListOf<LocalComment>()
    ('a'..'z').forEachIndexed { index, c ->
        posts.add(
            LocalComment(
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