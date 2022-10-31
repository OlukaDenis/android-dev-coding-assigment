package com.ensibuuko.android_dev_coding_assigment.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.model.PostEntity
import com.domain.model.sealed.Resource
import com.domain.usecases.posts.DeletePostUseCase
import com.domain.usecases.posts.FetchRemotePostsUseCase
import com.domain.usecases.posts.GetLocalPostsUseCase
import com.domain.usecases.users.GetSavedUserUseCase
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseUiSate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchRemotePostsUseCase: FetchRemotePostsUseCase,
    private val getSavedUserUseCase: GetSavedUserUseCase,
    private val getLocalPostsUseCase: GetLocalPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase
) : ViewModel() {

    private val _postListState = MutableLiveData<List<PostEntity>>()
    val postListState get() = _postListState

    private val _deleteState = MutableLiveData<BaseUiSate>()
    val deleteState get() = _deleteState

    fun fetchRemotePosts() {
        viewModelScope.launch {
            fetchRemotePostsUseCase().collect()
        }
    }

    fun getLocalPosts() {
        viewModelScope.launch {
            getLocalPostsUseCase().collect {
                _postListState.value = it
            }
        }
    }

    val getUser = runBlocking { getSavedUserUseCase().first() }

    fun deletePost(entity: PostEntity) {
        viewModelScope.launch {
            deletePostUseCase(entity.id).collect {
                when(it) {
                    is Resource.Loading -> _deleteState.value = BaseUiSate.Loading
                    is Resource.Success -> _deleteState.value = BaseUiSate.Success(it.data)
                    is Resource.Error -> _deleteState.value = BaseUiSate.Error(it.exception)
                }
            }
        }
    }

}