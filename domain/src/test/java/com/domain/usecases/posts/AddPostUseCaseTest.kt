package com.domain.usecases.posts

import com.domain.dispacher.AppDispatcher
import com.domain.model.PostEntity
import com.domain.model.sealed.Resource
import com.domain.repository.FakeLocalRepository
import com.domain.repository.FakePreferenceRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class AddPostUseCaseTest {
    @MockK
    lateinit var remote: RemoteRepository

    @MockK
    lateinit var utilRepository: UtilRepository

    @MockK
    lateinit var dispatcher: AppDispatcher

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var fakePreferenceRepository: FakePreferenceRepository
    private lateinit var addPostUseCase: AddPostUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fakeLocalRepository = FakeLocalRepository()
        fakePreferenceRepository = FakePreferenceRepository()

        addPostUseCase = AddPostUseCase(
            dispatcher,
            remote,
            fakeLocalRepository,
            fakePreferenceRepository,
            utilRepository
        )
    }

    @Test
    fun `Add a new post success`() = runBlocking {
        // Given
        coEvery { dispatcher.io } returns Dispatchers.Unconfined
        val post = PostEntity(12, "testing body", "Testing", 1, null, "", "")
        coEvery { remote.createPost(any()) } returns post

        // When
        val param = AddPostUseCase.Param(post.title, post.body)
        val result = addPostUseCase(param)

        // Then
        result.collect {
            coEvery { remote.createPost(any()) }

            when(it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    assertThat(it.data.title).isEqualTo("Testing")
                }
                is Resource.Error -> {}
            }
        }
    }
}