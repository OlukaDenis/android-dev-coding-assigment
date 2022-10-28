package com.data.local.localMappers

import com.data.UserPreferences
import com.domain.model.UserEntity
import javax.inject.Inject

class UserPreferenceMapper @Inject constructor() {
    fun toDomain(entity: UserPreferences): UserEntity {
        return UserEntity(
            id = entity.id,
            email = entity.email,
            username = entity.username,
            phone = entity.phone,
            website = entity.website
        )
    }

}