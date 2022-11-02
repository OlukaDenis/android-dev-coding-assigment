package com.domain.usecases.posts

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.PostEntity
import com.domain.model.sealed.Resource
import com.domain.repository.LocalRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import com.domain.utils.getDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class UpdatePostUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository,
    private val remote: RemoteRepository,
    private val utilRepository: UtilRepository
) : BaseFlowUseCase<UpdatePostUseCase.Param, Resource<PostEntity>>(dispatcher) {
    data class Param(
        val post: PostEntity
    )

    override fun run(param: Param?): Flow<Resource<PostEntity>> = flow {
        emit(Resource.Loading)
        try {
            param?.let {

                val request = HashMap<String, Any>().apply {
                    this["id"] = it.post.id
                    this["title"] = it.post.title
                    this["body"] = it.post.body
                    this["userId"] = it.post.userId
                }

                val result = remote.updatePost(it.post.id, request)
                result.createdAt = it.post.createdAt
                result.updatedAt = getDateTime()

                local.updatePost(result)

                emit(Resource.Success(result))
            }
        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }

    }
}