package com.github.masato1230.githubsincezero.presentation.screens.userdetail.states

import com.github.masato1230.githubsincezero.domain.model.GitHubEvent
import com.github.masato1230.githubsincezero.domain.model.GitHubRepositoryModel
import com.github.masato1230.githubsincezero.domain.model.GitHubUserAdditionalInfo
import com.github.masato1230.githubsincezero.domain.model.GitHubUserDetail

sealed class UserDetailListItemState {

    abstract val key: String

    data class UserDetail(
        override val key: String = "user_detail",
        val userDetail: GitHubUserDetail,
    ) : UserDetailListItemState()

    data object RepositorySectionTitle : UserDetailListItemState() {
        override val key: String = "repository_section_title"
    }

    data class Repositories(
        val repositories: List<GitHubRepositoryModel>,
    ) : UserDetailListItemState() {
        override val key: String = "repositories"
    }

    data object EventSectionTitle : UserDetailListItemState() {
        override val key: String = "event_section_title"
    }

    data object EventSectionEmpty : UserDetailListItemState() {
        override val key: String = "event_section_empty"
    }

    data class Event(
        val event: GitHubEvent,
        val isLastEvent: Boolean,
    ) : UserDetailListItemState() {
        override val key: String = "event_${event.id}"
    }

    data class Error(
        val sectionName: String,
    ) : UserDetailListItemState() {
        override val key: String = "error_$sectionName"
    }

    companion object {

        fun fromModels(
            additionalInfos: List<GitHubUserAdditionalInfo>,
        ): List<UserDetailListItemState> {
            val items = mutableListOf<UserDetailListItemState>()
            additionalInfos.toList().forEach { additionalInfo ->
                when (additionalInfo) {
                    is GitHubUserAdditionalInfo.Base -> {
                        items.add(UserDetail(userDetail = additionalInfo.userDetail))
                    }

                    is GitHubUserAdditionalInfo.Repositories -> {
                        items.add(RepositorySectionTitle)
                        additionalInfo.repositories.onSuccess { repositories ->
                            items.add(Repositories(repositories = repositories))
                        }.onFailure {
                            items.add(Error(sectionName = "repositories"))
                        }
                    }

                    is GitHubUserAdditionalInfo.Events -> {
                        items.add(EventSectionTitle)
                        additionalInfo.events.onSuccess { events ->
                            if (events.isEmpty()) {
                                items.add(EventSectionEmpty)
                            } else {
                                items.addAll(
                                    events.mapIndexed { index, it ->
                                        Event(
                                            event = it,
                                            isLastEvent = index == events.size - 1,
                                        )
                                    },
                                )
                            }
                        }.onFailure {
                            items.add(Error(sectionName = "events"))
                        }
                    }
                }
            }
            return items
        }
    }
}