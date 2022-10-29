package com.domain.usecases.users

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.UserEntity
import com.domain.model.sealed.Resource
import com.domain.repository.LocalRepository
import com.domain.repository.PreferenceRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

class FetchSingleUserUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository,
    private val remote: RemoteRepository,
    private val utilRepository: UtilRepository
) : BaseFlowUseCase<Long, Resource<UserEntity?>>(dispatcher) {

    override fun run(param: Long?): Flow<Resource<UserEntity?>> = flow {
        emit(Resource.Loading)
        try {
            param?.let {
                val response = remote.fetchSingleUser(it)
                local.saveUser(response)
                emit(Resource.Success(response))
            } ?: throw Exception("User id must not be null")
        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }
    }
}