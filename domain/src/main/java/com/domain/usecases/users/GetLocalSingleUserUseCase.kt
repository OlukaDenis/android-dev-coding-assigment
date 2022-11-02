package com.domain.usecases.users

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.UserEntity
import com.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import java.security.InvalidParameterException
import javax.inject.Inject

class GetLocalSingleUserUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository,
) : BaseFlowUseCase<Long, UserEntity?>(dispatcher) {

    override fun run(param: Long?): Flow<UserEntity?> {
        return param?.let {
            local.getUserById(it)
        } ?: throw InvalidParameterException()
    }
}