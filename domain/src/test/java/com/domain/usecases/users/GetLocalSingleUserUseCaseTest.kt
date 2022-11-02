package com.domain.usecases.users

import com.domain.dispacher.AppDispatcher
import com.domain.model.UserEntity
import com.domain.repository.FakeLocalRepository
import com.domain.utils.dummyUser
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetLocalSingleUserUseCaseTest {

    @MockK
    lateinit var dispatcher: AppDispatcher

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var getLocalSingleUserUseCase: GetLocalSingleUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fakeLocalRepository = FakeLocalRepository()
        getLocalSingleUserUseCase = GetLocalSingleUserUseCase(dispatcher, fakeLocalRepository)
    }

    @Test
    fun `Get local user by id success`() = runBlocking {

        // Given
        coEvery { dispatcher.io } returns Dispatchers.Unconfined

        fakeLocalRepository.saveUser(dummyUser)

        // When
        val result = getLocalSingleUserUseCase(10).first()

        // Then
        assertThat(result).isNotNull()
        assertThat(result?.email).isEqualTo("test@gmail.com")
    }

    @Test
    fun `Get a null local user by id`() = runBlocking {

        // Given
        coEvery { dispatcher.io } returns Dispatchers.Unconfined

        // When
        val result = getLocalSingleUserUseCase(10).first()

        // Then
        assertThat(result).isNull()
    }
}