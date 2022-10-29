package com.data.local.localMappers

import com.data.local.model.LocalPost
import com.domain.model.PostEntity
import javax.inject.Inject

class LocalPostMapper @Inject constructor(): BaseLocalMapper<LocalPost, PostEntity> {
    override fun toDomain(entity: LocalPost): PostEntity {
        return PostEntity(
            id = entity.id ,
            body = entity.body,
            title = entity.title,
            userId = entity.userId,
            user = null
        )
    }

    override fun toLocal(entity: PostEntity): LocalPost {
        return LocalPost(
            id = entity.id ,
            body = entity.body,
            title = entity.title,
            userId = entity.userId
        )
    }
}