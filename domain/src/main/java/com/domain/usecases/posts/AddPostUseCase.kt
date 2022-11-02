package com.domain.usecases.posts

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.PostEntity
import com.domain.model.sealed.Resource
import com.domain.repository.LocalRepository
import com.domain.repository.PreferenceRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import com.domain.utils.getDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException
import javax.inject.Inject

class AddPostUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val remote: RemoteRepository,
    private val local: LocalRepository,
    private val preferences: PreferenceRepository,
    private val utilRepository: UtilRepository
) : BaseFlowUseCase<AddPostUseCase.Param, Resource<PostEntity>>(dispatcher) {
    data class Param(
        val title: String,
        val body: String
    )

    override fun run(param: Param?): Flow<Resource<PostEntity>> = flow {
        emit(Resource.Loading)

        try {
            val user = preferences.getUser().first()

            param?.let {
                val request = HashMap<String, Any>().apply {
                    this["title"] = it.title
                    this["body"] = it.body
                    this["userId"] = user.id
                }

                val result = remote.createPost(request)
                result.createdAt = getDateTime()
                result.updatedAt = getDateTime()

                local.insertPost(result)

                emit(Resource.Success(result))
            } ?: throw InvalidParameterException()
        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }
    }
}