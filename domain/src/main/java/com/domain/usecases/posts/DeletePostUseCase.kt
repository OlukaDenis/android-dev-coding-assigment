package com.domain.usecases.posts

import com.domain.base.BaseSuspendUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.repository.LocalRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository
) : BaseSuspendUseCase<Long, Unit>(dispatcher) {
    override suspend fun run(param: Long?) {
        param?.let {
            local.deletePostById(it)
        }
    }

}