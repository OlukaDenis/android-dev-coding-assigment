package com.domain.usecases.comments

import com.domain.dispacher.AppDispatcher
import com.domain.model.sealed.Resource
import com.domain.repository.FakeLocalRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import com.domain.utils.getDummyComments
import com.domain.utils.getDummyPosts
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class FetchRemoteCommentsUseCaseTest {

    @MockK
    lateinit var remote: RemoteRepository

    @MockK
    lateinit var utilRepository: UtilRepository

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var fetchRemoteCommentsUseCase: FetchRemoteCommentsUseCase

    @MockK
    lateinit var dispatcher: AppDispatcher

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fakeLocalRepository = FakeLocalRepository()
        fetchRemoteCommentsUseCase =
            FetchRemoteCommentsUseCase(dispatcher, fakeLocalRepository, remote, utilRepository)
    }

    @Test
    fun `Fetch remote post comments list success`() = runBlocking {
        // Given
        coEvery { dispatcher.io } returns Dispatchers.Unconfined
        coEvery { remote.fetchPostComments(any()) } returns getDummyComments()

        // When
        val result = fetchRemoteCommentsUseCase(1)

        // Then
        result.collect {
            when(it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    coVerify { remote.fetchPostComments(1) }

                    assertThat(it.data).isNotNull()
                    assertThat(it.data).isNotEmpty()

                    val savedComments = fakeLocalRepository.getCommentsByPostId(1).first()
                    assertThat(savedComments).isNotEmpty()
                }
                is Resource.Error -> {}
            }
        }
    }
}