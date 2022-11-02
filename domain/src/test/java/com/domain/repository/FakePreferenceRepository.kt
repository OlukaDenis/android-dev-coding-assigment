package com.domain.repository

import com.domain.model.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePreferenceRepository: PreferenceRepository {

    private val userMap = HashMap<String, UserEntity>()
    private val passwordMap = HashMap<String, String>()

    override suspend fun saveUser(user: UserEntity) {
        userMap[USER_KEY] = user
    }

    override fun getUser(): Flow<UserEntity> {
        return flow {
            emit(userMap[USER_KEY] ?: UserEntity(0,"","","","","",))
        }
    }

    override suspend fun savePassword(input: String) {
        passwordMap[PASSWORD_KEY] = input
    }

    override fun getPassword(): Flow<String> {
        return flow {
            emit(passwordMap[PASSWORD_KEY] ?: "")
        }
    }

    companion object {
        const val USER_KEY = "user_key"
        const val PASSWORD_KEY = "password_key"
    }
}