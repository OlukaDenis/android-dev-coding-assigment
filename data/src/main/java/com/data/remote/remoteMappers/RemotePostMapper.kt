package com.data.remote.remoteMappers

import com.data.remote.model.RemotePost
import com.domain.model.PostEntity
import javax.inject.Inject

class RemotePostMapper @Inject constructor(): BaseRemoteMapper<RemotePost, PostEntity> {
    override fun mapToDomain(entity: RemotePost): PostEntity {
        return PostEntity(
            id = entity.id ?: throw Exception("Id must not be null"),
            body = entity.body.orEmpty(),
            title = entity.title.orEmpty(),
            userId = entity.userId ?: 0L,
            user = null,
            createdAt = entity.createdAt.orEmpty(),
            updatedAt = entity.updatedAt.orEmpty()
        )
    }
}