package com.domain.usecases.users

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.UserEntity
import com.domain.model.sealed.Resource
import com.domain.repository.LocalRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
            val user = local.getUser()
            val userId = when {
                user != null -> user.id
                param != null -> param
                else -> null
            }
            userId?.let {
                val response = remote.fetchSingleUser(it)

                response?.let { res ->
                    local.saveUserToDb(res)
                }

                emit(Resource.Success(response))
            } ?: throw Exception("User id must not be null")
        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }
    }
}