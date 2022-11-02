package com.domain.usecases.posts

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.sealed.Resource
import com.domain.repository.LocalRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository,
    private val remote: RemoteRepository,
    private val utilRepository: UtilRepository
) : BaseFlowUseCase<Long, Resource<Unit>>(dispatcher) {
    override fun run(param: Long?): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            param?.let {
                remote.deletePost(param)
                local.deletePostById(it)

                emit(Resource.Success(Unit))
            }
        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }

    }
}