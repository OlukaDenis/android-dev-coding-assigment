package com.domain.usecases.users

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.UserEntity
import com.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class GetSavedUserUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val preferences: PreferenceRepository,
) : BaseFlowUseCase<Unit, UserEntity>(dispatcher) {

    override fun run(param: Unit?): Flow<UserEntity>  {
        return preferences.getUser()
    }
}