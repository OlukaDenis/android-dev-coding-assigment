package com.domain.usecases.users

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedPasswordUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val preferences: PreferenceRepository,
) : BaseFlowUseCase<Unit, String>(dispatcher) {

    override fun run(param: Unit?): Flow<String> {
        return preferences.getPassword()
    }
}