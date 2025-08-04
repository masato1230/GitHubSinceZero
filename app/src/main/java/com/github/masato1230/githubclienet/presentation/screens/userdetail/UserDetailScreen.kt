package com.github.masato1230.githubclienet.presentation.screens.userdetail

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.masato1230.githubclienet.R
import com.github.masato1230.githubclienet.domain.model.GitHubRepositoryModel
import com.github.masato1230.githubclienet.presentation.components.error.CommunicationErrorView
import com.github.masato1230.githubclienet.presentation.screens.userdetail.components.UserDetailTopAppBar
import com.github.masato1230.githubclienet.presentation.screens.userdetail.sections.UserDetailBaseSection
import com.github.masato1230.githubclienet.presentation.screens.userdetail.sections.comopnents.UserDetailSectionErrorText
import com.github.masato1230.githubclienet.presentation.screens.userdetail.sections.comopnents.UserDetailSectionTitle
import com.github.masato1230.githubclienet.presentation.screens.userdetail.states.UserDetailListItemState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun UserDetailScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: UserDetailViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    onClickRepository: (GitHubRepositoryModel) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            UserDetailTopAppBar(
                login = viewModel.userLogin,
                avatarUrl = viewModel.avatarUrl,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
                scrollBehavior = scrollBehavior,
                onClickBack = onClickBack,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->

        when (val state = viewModel.state.value) {
            is UserDetailState.BaseLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is UserDetailState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    CommunicationErrorView(
                        onRetry = {
                            viewModel.onEvent(event = UserDetailEvent.OnRetry)
                        },
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }

            is UserDetailState.ShowList -> {
                UserDetailContent(
                    isCompletedLoading = state.isLoadingCompleted,
                    listItems = state.listItems,
                    modifier = Modifier.padding(paddingValues),
                    onClickRepository = onClickRepository,
                )
            }
        }
    }
}

@Composable
private fun UserDetailContent(
    isCompletedLoading: Boolean,
    listItems: List<UserDetailListItemState>,
    modifier: Modifier,
    onClickRepository: (GitHubRepositoryModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        items(
            listItems,
            key = { it.key }
        ) { listItem ->
            when (listItem) {
                is UserDetailListItemState.UserDetail -> {
                    UserDetailBaseSection(
                        userDetail = listItem.userDetail,
                    )
                }
                is UserDetailListItemState.RepositorySectionTitle -> {
                    UserDetailSectionTitle(
                        title = stringResource(id = R.string.user_detail_repository),
                    )
                }
                is UserDetailListItemState.Repositories -> {
                    // TODO
                }
                is UserDetailListItemState.EventSectionTitle -> {
                    UserDetailSectionTitle(
                        title = stringResource(id = R.string.user_detail_recent_activities),
                    )
                }
                is UserDetailListItemState.Event -> {
                    // TODO
                }
                is UserDetailListItemState.Error -> {
                    UserDetailSectionErrorText()
                }
            }
        }

        item(
            key = "loading_indicator",
        ) {
            if (!isCompletedLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}