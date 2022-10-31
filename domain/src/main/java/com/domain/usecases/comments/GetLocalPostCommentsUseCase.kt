package com.domain.usecases.comments

import com.domain.base.BaseFlowUseCase
import com.domain.dispacher.AppDispatcher
import com.domain.model.CommentEntity
import com.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import java.security.InvalidParameterException
import javax.inject.Inject


class GetLocalPostCommentsUseCase @Inject constructor(
    dispatcher: AppDispatcher,
    private val local: LocalRepository
) : BaseFlowUseCase<Long, List<CommentEntity>>(dispatcher) {

    override fun run(param: Long?): Flow<List<CommentEntity>> {
        return param?.let {
            local.getCommentsByPostId(it)
        } ?: throw InvalidParameterException()
    }

}