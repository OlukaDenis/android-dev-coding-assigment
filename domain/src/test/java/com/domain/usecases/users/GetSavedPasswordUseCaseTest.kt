package com.domain.usecases.users

import com.domain.dispacher.AppDispatcher
import com.domain.model.UserEntity
import com.domain.repository.FakePreferenceRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetSavedPasswordUseCaseTest {

    @MockK
    lateinit var dispatcher: AppDispatcher

    private lateinit var fakePreferenceRepository: FakePreferenceRepository
    private lateinit var getSavedPasswordUseCase: GetSavedPasswordUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fakePreferenceRepository = FakePreferenceRepository()
        getSavedPasswordUseCase = GetSavedPasswordUseCase(dispatcher, fakePreferenceRepository)
    }

    @Test
    fun `Get saved password success`() = runBlocking {
        // Given
        coEvery { dispatcher.io } returns Dispatchers.Unconfined
        fakePreferenceRepository.savePassword("1234")

        // When
        val result = getSavedPasswordUseCase().first()

        // Then
        assertThat(result).isEqualTo("1234")
    }

    @Test
    fun `Get saved empty password`() = runBlocking {
        // Given
        coEvery { dispatcher.io } returns Dispatchers.Unconfined

        // When
        val result = getSavedPasswordUseCase().first()

        // Then
        assertThat(result).isEmpty()
    }
}