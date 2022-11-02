package com.data.local.localMappers

import com.data.local.model.LocalComment
import com.domain.model.CommentEntity
import javax.inject.Inject

class LocalCommentMapper @Inject constructor(): BaseLocalMapper<LocalComment, CommentEntity> {
    override fun toDomain(entity: LocalComment): CommentEntity {
        return CommentEntity(
            id = entity.id,
            postId = entity.postId,
            name = entity.name,
            body = entity.body,
            email = entity.email,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    override fun toLocal(entity: CommentEntity): LocalComment {
        return LocalComment(
            id = entity.id,
            postId = entity.postId,
            name = entity.name,
            body = entity.body,
            email = entity.email,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }


}