package com.data.local.localMappers

import com.data.local.model.LocalUser
import com.domain.model.UserEntity
import javax.inject.Inject

class LocalUserMapper @Inject constructor(): BaseLocalMapper<LocalUser, UserEntity> {
    override fun toDomain(entity: LocalUser): UserEntity {
        return UserEntity(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            username = entity.username,
            phone = entity.phone,
            website = entity.website
        )
    }

    override fun toLocal(entity: UserEntity): LocalUser {
        return LocalUser(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            username = entity.username,
            phone = entity.phone,
            website = entity.website
        )
    }

}