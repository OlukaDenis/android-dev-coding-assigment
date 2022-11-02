package com.domain.usecases.users

import com.domain.dispacher.AppDispatcher
import com.domain.model.UserEntity
import com.domain.model.sealed.Resource
import com.domain.repository.FakeLocalRepository
import com.domain.repository.FakePreferenceRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import com.domain.utils.dummyUser
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

class FetchSingleUserUseCaseTest {

    @MockK
    lateinit var remote: RemoteRepository

    @MockK
    lateinit var utilRepository: UtilRepository

    @MockK
    lateinit var dispatcher: AppDispatcher

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var fetchSingleUserUseCase: FetchSingleUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fakeLocalRepository = FakeLocalRepository()
        fetchSingleUserUseCase =
            FetchSingleUserUseCase(dispatcher, fakeLocalRepository, remote, utilRepository)
    }

    @Test
    fun `Fetch remote user success`() = runBlocking {
        // Given
        coEvery { dispatcher.io } returns Dispatchers.Unconfined
        val user = dummyUser
        coEvery { remote.fetchSingleUser(any()) } returns user

        // When
        val result = fetchSingleUserUseCase(10)

        // Then
        result.collect {
            when(it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    coVerify { remote.fetchSingleUser(any()) }

                    assertThat(it.data).isNotNull()
                    assertThat(it.data?.email).isEqualTo("test@gmail.com")

                    val savedUser = fakeLocalRepository.getUserById(10).first()
                    assertThat(savedUser?.email).isEqualTo("test@gmail.com")
                }
                is Resource.Error -> {}
            }
        }
    }

    @Test
    fun `Fetch remote user failure`() = runBlocking {
        // Given
        coEvery { dispatcher.io } returns Dispatchers.Unconfined
        coEvery { utilRepository.getNetworkError(any()) } returns "User id must not be null"

        // When
        val result = fetchSingleUserUseCase()

        // Then
        result.collect {
            when(it) {
                is Resource.Loading -> {}
                is Resource.Success -> {}
                is Resource.Error -> {
                    assertThat(it.exception).isEqualTo("User id must not be null")
                }
            }
        }

    }
}