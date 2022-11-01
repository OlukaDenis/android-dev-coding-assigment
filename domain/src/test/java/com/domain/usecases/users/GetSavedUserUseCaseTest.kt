package com.domain.usecases.users

import com.domain.dispacher.AppDispatcher
import com.domain.model.UserEntity
import com.domain.repository.FakePreferenceRepository
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSavedUserUseCaseTest {

    @MockK
    lateinit var dispatcher: AppDispatcher

    private lateinit var fakePreferenceRepository: FakePreferenceRepository
    private lateinit var getSavedUserUseCase: GetSavedUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fakePreferenceRepository = FakePreferenceRepository()
        getSavedUserUseCase = GetSavedUserUseCase(dispatcher, fakePreferenceRepository)
    }

    @Test
    fun `Get saved pref user success`() = runBlocking {
        // Given
        coEvery { dispatcher.io } returns Dispatchers.Unconfined
        val user = UserEntity(10L,"Test", "test@gmail.com", "90898172", "1234", "")
        fakePreferenceRepository.saveUser(user)

        // When
        val result = getSavedUserUseCase().first()

        // Then
        Truth.assertThat(result.email).isEqualTo("test@gmail.com")
    }

    @Test
    fun `Get saved empty pref user`() = runBlocking {
        // Given
        coEvery { dispatcher.io } returns Dispatchers.Unconfined

        // When
        val result = getSavedUserUseCase().first()

        // Then
        Truth.assertThat(result.name).isEmpty()
    }
}