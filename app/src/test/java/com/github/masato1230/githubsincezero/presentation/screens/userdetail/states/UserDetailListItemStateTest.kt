package com.github.masato1230.githubsincezero.presentation.screens.userdetail.states

import com.github.masato1230.githubsincezero.domain.model.GitHubEvent
import com.github.masato1230.githubsincezero.domain.model.GitHubRepositoryModel
import com.github.masato1230.githubsincezero.domain.model.GitHubUserAdditionalInfo
import com.github.masato1230.githubsincezero.domain.model.GitHubUserDetail
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UserDetailListItemStateTest {

    @Nested
    inner class WhenAllDataIsSuccessfullyFetched {
        // テストデータ
        private lateinit var additionalInfos: List<GitHubUserAdditionalInfo>
        private val userDetail = GitHubUserDetail.createDummy()
        private val repositories = listOf(GitHubRepositoryModel.createDummy())
        private val events = listOf(GitHubEvent.createDummy())

        @BeforeEach
        fun setUp() {
            additionalInfos = listOf(
                GitHubUserAdditionalInfo.Base(userDetail = userDetail),
                GitHubUserAdditionalInfo.Repositories(repositories = Result.success(repositories)),
                GitHubUserAdditionalInfo.Events(events = Result.success(events))
            )
        }

        @Test
        fun `should return a list of UserDetailListItemState correctly`() {
            // Act
            val result = UserDetailListItemState.fromModels(additionalInfos)

            // Assert
            assertEquals(5, result.size)
            assertTrue(result[0] is UserDetailListItemState.UserDetail)
            assertTrue(result[1] is UserDetailListItemState.RepositorySectionTitle)
            assertTrue(result[2] is UserDetailListItemState.Repositories)
            assertTrue(result[3] is UserDetailListItemState.EventSectionTitle)
            assertTrue(result[4] is UserDetailListItemState.Event)
        }

        @Test
        fun `should correctly convert all data models`() {
            // Act
            val result = UserDetailListItemState.fromModels(additionalInfos)
            val userDetailItem = result[0] as UserDetailListItemState.UserDetail
            val repositoriesItem = result[2] as UserDetailListItemState.Repositories
            val eventItem = result[4] as UserDetailListItemState.Event

            // Assert
            assertEquals(userDetail, userDetailItem.userDetail)
            assertEquals(repositories, repositoriesItem.repositories)
            assertEquals(events[0], eventItem.event)
        }

        @Test
        fun `last event should have isLastEvent flag set to true`() {
            // Act
            val result = UserDetailListItemState.fromModels(additionalInfos)
            val eventItem = result.last() as UserDetailListItemState.Event

            // Assert
            assertTrue(eventItem.isLastEvent)
        }
    }

    @Nested
    inner class WhenDataFetchingFails {
        private val exception = Exception("Network Error")

        @Test
        fun `if repository fetching fails, should return a title and an error item`() {
            // Arrange
            val additionalInfos = listOf(
                GitHubUserAdditionalInfo.Repositories(repositories = Result.failure(exception))
            )

            // Act
            val result = UserDetailListItemState.fromModels(additionalInfos)

            // Assert
            assertEquals(2, result.size)
            assertTrue(result[0] is UserDetailListItemState.RepositorySectionTitle)
            assertTrue(result[1] is UserDetailListItemState.Error)
            assertEquals("repositories", (result[1] as UserDetailListItemState.Error).sectionName)
        }

        @Test
        fun `if event fetching fails, should return a title and an error item`() {
            // Arrange
            val additionalInfos = listOf(
                GitHubUserAdditionalInfo.Events(events = Result.failure(exception))
            )

            // Act
            val result = UserDetailListItemState.fromModels(additionalInfos)

            // Assert
            assertEquals(2, result.size)
            assertTrue(result[0] is UserDetailListItemState.EventSectionTitle)
            assertTrue(result[1] is UserDetailListItemState.Error)
            assertEquals("events", (result[1] as UserDetailListItemState.Error).sectionName)
        }
    }

    @Nested
    inner class WhenDataIsMixed {
        @Test
        fun `should handle a mix of successful and failed fetches correctly`() {
            // Arrange
            val additionalInfos = listOf(
                GitHubUserAdditionalInfo.Base(userDetail = GitHubUserDetail.createDummy()),
                GitHubUserAdditionalInfo.Repositories(repositories = Result.failure(Exception("Repo error"))),
                GitHubUserAdditionalInfo.Events(events = Result.success(listOf(GitHubEvent.createDummy())))
            )

            // Act
            val result = UserDetailListItemState.fromModels(additionalInfos)

            // Assert
            assertEquals(5, result.size)
            assertTrue(result[0] is UserDetailListItemState.UserDetail)
            assertTrue(result[1] is UserDetailListItemState.RepositorySectionTitle)
            assertTrue(result[2] is UserDetailListItemState.Error)
            assertTrue(result[3] is UserDetailListItemState.EventSectionTitle)
            assertTrue(result[4] is UserDetailListItemState.Event)
        }
    }
}