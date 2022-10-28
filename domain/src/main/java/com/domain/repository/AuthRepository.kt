package com.domain.repository

interface AuthRepository {

    suspend fun authenticateUser(): String
}