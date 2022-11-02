package com.domain.usecases.posts

import com.domain.dispacher.AppDispatcher
import com.domain.model.sealed.Resource
import com.domain.repository.FakeLocalRepository
import com.domain.repository.FakePreferenceRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import com.domain.utils.getDummyPosts
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

class GetUserPostsUseCaseTest {

    @MockK
    lateinit var dispatcher: AppDispatcher

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var fakePreferenceRepository: FakePreferenceRepository
    private lateinit var getUserPostsUseCase: GetUserPostsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fakeLocalRepository = FakeLocalRepository()
        fakePreferenceRepository = FakePreferenceRepository()
        getUserPostsUseCase = GetUserPostsUseCase(dispatcher, fakeLocalRepository, fakePreferenceRepository)
    }

    @Test
    fun `Fetch local user post list success`() = runBlocking {
        // Given
        coEvery { dispatcher.io } returns Dispatchers.Unconfined
        getDummyPosts().forEach {
            fakeLocalRepository.insertPost(it)
        }

        // When
        val result = getUserPostsUseCase(10).first()

        // Then
        assertThat(result).isNotNull()
        assertThat(result).isNotEmpty()
    }
}