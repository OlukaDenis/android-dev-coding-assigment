package com.data.remote.remoteMappers

import com.data.remote.model.RemoteComment
import com.domain.model.CommentEntity
import javax.inject.Inject

class RemoteCommentMapper @Inject constructor(): BaseRemoteMapper<RemoteComment, CommentEntity> {
    override fun mapToDomain(entity: RemoteComment): CommentEntity {
        return CommentEntity(
            id = entity.id ?: throw Exception("Id cannot be null"),
            postId = entity.postId ?: 0L,
            name = entity.name.orEmpty(),
            body = entity.body.orEmpty(),
            email = entity.email.orEmpty(),
            createdAt = entity.createdAt.orEmpty(),
            updatedAt = entity.updatedAt.orEmpty()
        )
    }
}