package com.domain.usecases.comments

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.CommentEntity
import com.domain.model.sealed.Resource
import com.domain.repository.LocalRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import com.domain.utils.getDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class UpdateCommentUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository,
    private val remote: RemoteRepository,
    private val utilRepository: UtilRepository
) : BaseFlowUseCase<UpdateCommentUseCase.Param, Resource<CommentEntity>>(dispatcher) {
    data class Param(
        val comment: CommentEntity
    )

    override fun run(param: Param?): Flow<Resource<CommentEntity>> = flow {
        emit(Resource.Loading)
        try {
            param?.let {

                val request = HashMap<String, Any>().apply {
                    this["id"] = it.comment.id
                    this["email"] = it.comment.email
                    this["name"] = it.comment.name
                    this["body"] = it.comment.body
                    this["postId"] = it.comment.postId
                }

                val result = remote.updateComment(it.comment.id, request)
                result.createdAt = it.comment.createdAt
                result.updatedAt = getDateTime()

                local.updateComment(result)

                emit(Resource.Success(result))
            }
        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }

    }
}