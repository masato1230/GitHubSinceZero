package com.github.masato1230.githubsincezero.presentation.screens.userdetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.github.masato1230.githubsincezero.domain.model.GitHubRepositoryModel
import com.github.masato1230.githubsincezero.domain.model.GitHubUserAdditionalInfo
import com.github.masato1230.githubsincezero.domain.model.GitHubUserDetail
import com.github.masato1230.githubsincezero.domain.usecases.GetGitHubUserDetailUseCase
import com.github.masato1230.githubsincezero.presentation.AppRoute
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserDetailViewModelTest {

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var gitHubUserDetailUseCase: GetGitHubUserDetailUseCase
    private lateinit var viewModel: UserDetailViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val login = "testUser"
    private val avatarUrl = "http://example.com/avatar.jpg"

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        savedStateHandle = mockk(relaxed = true)
        every { savedStateHandle.get<String>(AppRoute.UserDetail.KEY_USER_LOGIN) } returns login
        every { savedStateHandle.get<String>(AppRoute.UserDetail.KEY_AVATAR_URL) } returns avatarUrl
        gitHubUserDetailUseCase = mockk()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Nested
    inner class InitializationTests {
        @Test
        fun `when viewModel is initialized, state should be BaseLoading, login, and avatarUrl should be set`() = runTest {
            // Arrange
            coEvery { gitHubUserDetailUseCase(login) } returns flow {
                emit(emptyList<GitHubUserAdditionalInfo>() to true)
            }

            // Act
            viewModel = UserDetailViewModel(savedStateHandle, gitHubUserDetailUseCase)

            // Assert
            assertEquals(UserDetailState.BaseLoading, viewModel.state.value)
            assertEquals(login, viewModel.userLogin)
            assertEquals(avatarUrl, viewModel.avatarUrl)
        }
    }

    @Nested
    inner class DataLoadingTests {
        @Test
        fun `when data is fetched successfully, state should be updated correctly`() = runTest {
            // Arrange
            val userDetail = GitHubUserDetail.createDummy()
            val repositories = listOf(GitHubRepositoryModel.createDummy())
            val sections = listOf(
                GitHubUserAdditionalInfo.Base(userDetail),
                GitHubUserAdditionalInfo.Repositories(Result.success(repositories))
            )

            coEvery { gitHubUserDetailUseCase(login) } returns flow {
                emit(listOf(sections[0]) to false)
                emit(sections to true)
            }

            // Act
            viewModel = UserDetailViewModel(savedStateHandle, gitHubUserDetailUseCase)

            // Assert
            viewModel.state.test {
                // assert initial state
                assertEquals(UserDetailState.BaseLoading, awaitItem())

                // assert first emit
                val firstEmit = awaitItem() as UserDetailState.ShowList
                assertEquals(1, firstEmit.listItems.size)
                assertEquals(false, firstEmit.isLoadingCompleted)

                // assert second emit
                val secondEmit = awaitItem() as UserDetailState.ShowList
                assertEquals(3, secondEmit.listItems.size)
                assertEquals(true, secondEmit.isLoadingCompleted)
            }
        }
    }

    @Nested
    inner class ErrorHandlingTests {
        @Test
        fun `when use case throws an exception, state should be Error`() = runTest {
            // Arrange
            val exception = RuntimeException("Network Error")
            coEvery { gitHubUserDetailUseCase(login) } returns flow { throw exception }

            // Act
            viewModel = UserDetailViewModel(savedStateHandle, gitHubUserDetailUseCase)

            // Assert
            viewModel.state.test {
                assertEquals(UserDetailState.BaseLoading, awaitItem())
                assertEquals(UserDetailState.Error(exception), awaitItem())
            }
        }
    }
}