package com.domain.usecases.posts

import com.domain.dispacher.AppDispatcher
import com.domain.model.sealed.Resource
import com.domain.repository.FakeLocalRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import com.domain.usecases.users.FetchSingleUserUseCase
import com.domain.utils.getDummyPosts
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchRemotePostsUseCaseTest {

    @MockK
    lateinit var remote: RemoteRepository

    @MockK
    lateinit var utilRepository: UtilRepository

    @MockK
    lateinit var dispatcher: AppDispatcher

    private lateinit var fetchSingleUserUseCase: FetchSingleUserUseCase

    private lateinit var fetchRemotePostsUseCase: FetchRemotePostsUseCase

    private lateinit var fakeLocalRepository: FakeLocalRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fakeLocalRepository = FakeLocalRepository()
        fetchSingleUserUseCase =
            FetchSingleUserUseCase(dispatcher, fakeLocalRepository, remote, utilRepository)
        fetchRemotePostsUseCase =
            FetchRemotePostsUseCase(dispatcher, fakeLocalRepository, remote, utilRepository, fetchSingleUserUseCase)
    }

    @Test
    fun `Fetch remote post list success`() = runBlocking {
        // Given
        coEvery { dispatcher.io } returns Dispatchers.Unconfined
        coEvery { remote.fetchPosts() } returns getDummyPosts()
        coEvery { utilRepository.getNetworkError(any()) } returns "Exception"

        // When
        val result = fetchRemotePostsUseCase()

        // Then
        result.collect {
            when(it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    coVerify { remote.fetchPosts() }

                    Truth.assertThat(it.data).isNotNull()
                    Truth.assertThat(it.data).isNotEmpty()
                }
                is Resource.Error -> {}
            }
        }
    }
}