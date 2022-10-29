package com.domain.usecases.posts

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.PostEntity
import com.domain.repository.LocalRepository
import com.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetLocalPostsUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository,
    private val remote: RemoteRepository
) : BaseFlowUseCase<Unit, List<PostEntity>>(dispatcher) {

    override fun run(param: Unit?): Flow<List<PostEntity>> {
        return local.getPosts().map { list ->
            list.map {
                val user = runBlocking {
                    local.getUserById(it.userId).first()
                }

                it.user = user

                it
            }
        }
    }

}