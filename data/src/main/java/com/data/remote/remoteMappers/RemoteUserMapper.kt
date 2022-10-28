package com.data.remote.remoteMappers

import com.data.remote.model.RemoteUser
import com.domain.model.UserEntity
import javax.inject.Inject

class RemoteUserMapper @Inject constructor(): BaseRemoteMapper<RemoteUser, UserEntity> {
    override fun mapToDomain(entity: RemoteUser): UserEntity {
        return UserEntity(
            id = entity.id ?: throw Exception("User id cannot be null"),
            email = entity.email.orEmpty(),
            username = entity.username.orEmpty(),
            phone = entity.phone.orEmpty(),
            website = entity.website.orEmpty()
        )
    }
}