package com.domain.usecases.posts

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.PostEntity
import com.domain.model.sealed.Resource
import com.domain.repository.LocalRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import com.domain.usecases.users.FetchSingleUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class FetchRemotePostsUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository,
    private val remote: RemoteRepository,
    private val utilRepository: UtilRepository,
    private val fetchSingleUserUseCase: FetchSingleUserUseCase
) : BaseFlowUseCase<Unit, Resource<List<PostEntity>>>(dispatcher) {
    override fun run(param: Unit?): Flow<Resource<List<PostEntity>>> = flow {
        emit(Resource.Loading)

        try {
            val posts = remote.fetchPosts()

            posts.map {
              runBlocking {
                    local.getUserById(it.userId).first()
                } ?: fetchSingleUserUseCase(it.userId).collect()

                local.insertPost(it)
            }

            emit(Resource.Success(posts))
        } catch (throwable: Throwable) {
            emit(Resource.Error(utilRepository.getNetworkError(throwable)))
        }
    }
}