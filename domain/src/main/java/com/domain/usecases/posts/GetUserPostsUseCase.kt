package com.domain.usecases.posts

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.PostEntity
import com.domain.repository.LocalRepository
import com.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.security.InvalidParameterException
import javax.inject.Inject

class GetUserPostsUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository,
    private val preferences: PreferenceRepository
) : BaseFlowUseCase<Long, List<PostEntity>>(dispatcher) {

    override fun run(param: Long?): Flow<List<PostEntity>> {
        return param?.let {
            local.getPostsByUserId(param).map { list ->
                list.map {
                    val currentUser = runBlocking { preferences.getUser().first() }
                    val postUser = if (it.userId == currentUser.id) currentUser else runBlocking {
                        local.getUserById(it.userId).first()
                    }

                    it.user = postUser
                    it
                }
            }
        } ?: throw InvalidParameterException()
    }

}