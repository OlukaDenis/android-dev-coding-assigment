package com.domain.usecases.users

import com.domain.dispacher.AppDispatcher
import com.domain.model.PostEntity
import com.domain.model.UserEntity
import com.domain.model.sealed.Resource
import com.domain.repository.FakeLocalRepository
import com.domain.repository.FakePreferenceRepository
import com.domain.repository.RemoteRepository
import com.domain.repository.UtilRepository
import com.domain.utils.getDummyUsers
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import java.io.IOException

class CreateUserUseCaseTest {

    @MockK
    lateinit var remote: RemoteRepository

    @MockK
    lateinit var utilRepository: UtilRepository

    @MockK
    lateinit var dispatcher: AppDispatcher

    private lateinit var fakeLocalRepository: FakeLocalRepository
    private lateinit var fakePreferenceRepository: FakePreferenceRepository
    private lateinit var createUserUseCase: CreateUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fakeLocalRepository = FakeLocalRepository()
        fakePreferenceRepository = FakePreferenceRepository()
        createUserUseCase = CreateUserUseCase(
            dispatcher,
            remote,
            fakeLocalRepository,
            fakePreferenceRepository,
            utilRepository
        )
    }

    @Test
    fun `Create user success`() = runBlocking {

        // Given
        val user = UserEntity(1L,"Test", "test@gmail.com", "90898172", "1234", "")
        coEvery { dispatcher.io } returns Dispatchers.Unconfined
        coEvery { remote.createUser(any()) } returns user
        val param = CreateUserUseCase.Param(user.name, user.email, user.phone, user.phone)

        // When
        val result = createUserUseCase(param)

        // Then
        result.collect {
            coEvery { remote.createUser(any()) }
            when(it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    assertThat(it.data.name).isNotEmpty()
                    assertThat(it.data.name).isEqualTo("Test")

                    val savedPrefUser = fakePreferenceRepository.getUser().first()
                    assertThat(savedPrefUser.name).isEqualTo("Test")

                    val dbUser = fakeLocalRepository.getUser()
                    assertThat(dbUser).isNotNull()
                    assertThat(dbUser?.name).isEqualTo("Test")
                }
                is Resource.Error -> {}
            }
        }
    }

    @Test
    fun `Create user failure`() = runBlocking {

        // Given
        val user = UserEntity(1L,"Test", "test@gmail.com", "90898172", "1234", "")
        coEvery { dispatcher.io } returns Dispatchers.Unconfined
        coEvery { remote.createUser(any()) } throws IOException()
        coEvery { utilRepository.getNetworkError(any()) } returns "IO exception"
        val param = CreateUserUseCase.Param(user.name, user.email, user.phone, user.phone)

        // When
        val result = createUserUseCase(param)

        // Then
        result.collect {
            coEvery { remote.createUser(any()) }

            when(it) {
                is Resource.Loading -> {}
                is Resource.Success -> {}
                is Resource.Error -> {
                    assertThat(it.exception).isNotEmpty()
                    assertThat(it.exception).isEqualTo("IO exception")
                }
            }
        }
    }
}
