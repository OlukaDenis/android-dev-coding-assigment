package com.domain.usecases.posts

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.PostEntity
import com.domain.repository.LocalRepository
import com.domain.repository.PreferenceRepository
import com.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetLocalPostsUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository,
    private val preferences: PreferenceRepository
) : BaseFlowUseCase<Unit, List<PostEntity>>(dispatcher) {

    override fun run(param: Unit?): Flow<List<PostEntity>> {
        return local.getPosts().map { list ->
            list.map {
                val currentUser = runBlocking { preferences.getUser().first() }
                val postUser = if (it.userId == currentUser.id) currentUser else runBlocking {
                    local.getUserById(it.userId).first()
                }

                it.user = postUser
                it
            }
        }
    }

}