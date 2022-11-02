package com.ensibuuko.android_dev_coding_assigment.ui.auth.signup


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.domain.model.UserEntity
import com.domain.model.sealed.Resource
import com.domain.usecases.users.CreateUserUseCase
import com.ensibuuko.android_dev_coding_assigment.ui.base.BaseUiSate
import com.ensibuuko.android_dev_coding_assigment.utils.dummyUser
import com.ensibuuko.android_dev_coding_assigment.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignupViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val createUserUseCase: CreateUserUseCase = mockk()

    private lateinit var viewModel: SignupViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        viewModel = SignupViewModel(createUserUseCase)
    }

    @Test
    fun `Create user success`() = runBlocking {
        // Given
        coEvery { createUserUseCase(any()) } returns flowOf(Resource.Success(dummyUser))

        // When
        viewModel.createUser("Test", "test@gmail.com", "", "")

        // Then
        val signupState = viewModel.signupState.getOrAwaitValue()
        assertThat(signupState).isNotNull()

        when(signupState) {
            is BaseUiSate.Loading -> {}
            is BaseUiSate.Error -> {}
            is BaseUiSate.Success<*> -> {
                val user = signupState.data as UserEntity
                assertThat(user.email).isEqualTo("test@gmail.com")
            }
        }
    }

    @Test
    fun `Create user failure`() = runBlocking {
        // Given
        coEvery { createUserUseCase(any()) } returns flowOf(Resource.Error("Email required"))

        // When
        viewModel.createUser("", "", "", "")

        // Then
        val signupState = viewModel.signupState.getOrAwaitValue()
        assertThat(signupState).isNotNull()

        when(signupState) {
            is BaseUiSate.Loading -> {}
            is BaseUiSate.Error -> {
                assertThat(signupState.message).isEqualTo("Email required")
            }
            is BaseUiSate.Success<*> -> {}
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}