package com.domain.usecases.comments

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.sealed.Resource
import com.domain.repository.LocalRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository,
    private val remote: RemoteRepository,
    private val utilRepository: UtilRepository
) : BaseFlowUseCase<Long, Resource<Unit>>(dispatcher) {
    override fun run(param: Long?): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            param?.let {
                remote.deleteComment(param)
                local.deleteCommentById(it)

                emit(Resource.Success(Unit))
            }
        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }

    }
}