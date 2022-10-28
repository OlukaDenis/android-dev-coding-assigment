package com.domain.usecases.users

import com.domain.base.BaseSuspendUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.UserEntity
import com.domain.repository.LocalRepository
import javax.inject.Inject

class GetSavedUserUseCase @Inject constructor(
    private val  dispatcher: AppDispatcher,
    private val local: LocalRepository,
) : BaseSuspendUseCase<Unit, UserEntity?>(dispatcher) {

    override suspend fun run(param: Unit?): UserEntity? {
        return local.getUser()
    }
}