package com.data.remote.impl

import com.data.remote.services.AuthService
import com.domain.repository.AuthRepository
import com.domain.repository.LocalRepository
import com.domain.repository.PreferenceRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val preferences: PreferenceRepository,
    private val local: LocalRepository
): AuthRepository {

    override suspend fun authenticateUser(): String {
        return try {
            val user = local.getUser()
            user?.let {
//                val params = HashMap<String, String>().apply {
//                    this["phoneNumber"] = it.phoneNumber
//                    this["userId"] = it.id
//                }
//                val response = authService.authenticateUser(params)

                // Save token]
                "User"
            } ?: "User must not be null"

        } catch (throwable: Throwable) {
            throw throwable
        }
    }
}