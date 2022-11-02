package com.ensibuuko.android_dev_coding_assigment.ui.posts.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.model.PostEntity
import com.domain.model.sealed.Resource
import com.domain.usecases.posts.AddPostUseCase
import com.domain.usecases.posts.UpdatePostUseCase
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseUiSate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val addPostUseCase: AddPostUseCase,
    private val updatePostUseCase: UpdatePostUseCase
): ViewModel() {

    private var _addPostState = MutableLiveData<BaseUiSate>()
    val addPostState get() = _addPostState

    fun addPost(title: String, body: String) {
        viewModelScope.launch {
            addPostUseCase(AddPostUseCase.Param(title, body)).collect {
                when(it) {
                    is Resource.Loading -> _addPostState.value = BaseUiSate.Loading
                    is Resource.Success -> _addPostState.value = BaseUiSate.Success(it.data)
                    is Resource.Error -> _addPostState.value = BaseUiSate.Error(it.exception)
                }
            }
        }
    }


    fun updatePost(entity: PostEntity) {
        viewModelScope.launch {
            updatePostUseCase(UpdatePostUseCase.Param(entity)).collect {
                when(it) {
                    is Resource.Loading -> _addPostState.value = BaseUiSate.Loading
                    is Resource.Success -> _addPostState.value = BaseUiSate.Success(it.data)
                    is Resource.Error -> _addPostState.value = BaseUiSate.Error(it.exception)
                }
            }
        }
    }
}