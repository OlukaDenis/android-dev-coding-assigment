package com.ensibuuko.android_dev_coding_assigment.ui.posts.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.model.CommentEntity
import com.domain.model.PostEntity
import com.domain.usecases.comments.*
import com.domain.usecases.users.GetSavedUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val getLocalPostCommentsUseCase: GetLocalPostCommentsUseCase,
    private val fetchRemoteCommentsUseCase: FetchRemoteCommentsUseCase,
    private val getSavedUserUseCase: GetSavedUserUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val updateCommentUseCase: UpdateCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase
): ViewModel() {

    private val _commentListState = MutableLiveData<List<CommentEntity>>()
    val commentListState get() = _commentListState

    var commentType = CommentType.COMMENT
    var selectedComment: CommentEntity? = null

    val getUser = runBlocking { getSavedUserUseCase().first() }

    fun fetchRemoteComments(postId: Long) {
        viewModelScope.launch {
            fetchRemoteCommentsUseCase(postId).collect()
        }
    }

    fun saveComment(body: String, entity: PostEntity) {
        viewModelScope.launch {
            addCommentUseCase(AddCommentUseCase.Param(
                body = body,
                name = entity.title,
                postId = entity.id
            )).collect()
        }
    }

    fun getLocalComments(postId: Long) {
        viewModelScope.launch {
            getLocalPostCommentsUseCase(postId).collect {
                _commentListState.value = it
            }
        }
    }

    fun updateComment(entity: CommentEntity) {
        viewModelScope.launch {
            updateCommentUseCase(UpdateCommentUseCase.Param(entity)).collect()
        }
    }

    fun deleteComment(entity: CommentEntity) {
        viewModelScope.launch {
            deleteCommentUseCase(entity.id).collect()
        }
    }

    enum class CommentType{
        COMMENT,
        EDIT
    }
}