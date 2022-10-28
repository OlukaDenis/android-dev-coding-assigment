package com.domain.usecases.users

import com.domain.repository.LocalRepository
import javax.inject.Inject

class ClearUsersUseCase @Inject constructor(
    private val local: LocalRepository
) {

    suspend operator fun invoke() = local.clearUsers()
}