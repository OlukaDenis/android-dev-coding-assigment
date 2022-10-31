package com.domain.usecases.comments

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.CommentEntity
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

class AddCommentUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val remote: RemoteRepository,
    private val local: LocalRepository,
    private val preferences: PreferenceRepository,
    private val utilRepository: UtilRepository
) : BaseFlowUseCase<AddCommentUseCase.Param, Resource<CommentEntity>>(dispatcher) {
    data class Param(
        val body: String,
        val postId: Long,
        val name: String
    )

    override fun run(param: Param?): Flow<Resource<CommentEntity>> = flow {
        emit(Resource.Loading)

        try {
            val user = preferences.getUser().first()

            param?.let {
                val request = HashMap<String, Any>().apply {
                    this["body"] = it.body
                    this["name"] = it.name
                    this["postId"] = it.postId
                    this["email"] = user.email
                }

                val result = remote.createComment(request)
                result.createdAt = getDateTime()
                result.updatedAt = getDateTime()

                local.insertComment(result)

                emit(Resource.Success(result))
            } ?: throw InvalidParameterException()
        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }
    }
}