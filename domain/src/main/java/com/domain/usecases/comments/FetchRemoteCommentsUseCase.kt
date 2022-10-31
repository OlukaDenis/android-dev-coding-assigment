package com.domain.usecases.comments

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.CommentEntity
import com.domain.model.sealed.Resource
import com.domain.repository.LocalRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import com.domain.usecases.users.FetchSingleUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchRemoteCommentsUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository,
    private val remote: RemoteRepository,
    private val utilRepository: UtilRepository,
    private val fetchSingleUserUseCase: FetchSingleUserUseCase
) : BaseFlowUseCase<Long, Resource<List<CommentEntity>>>(dispatcher) {
    override fun run(param: Long?): Flow<Resource<List<CommentEntity>>> = flow {
        emit(Resource.Loading)

        try {
            param?.let {
                val result = remote.fetchPostComments(it)
                
                result.map {
                    
                }

                emit(Resource.Success(result))
            }
           
        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }
    }
}