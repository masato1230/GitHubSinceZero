package com.github.masato1230.githubclienet.presentation.screens.userdetail

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.masato1230.githubclienet.R
import com.github.masato1230.githubclienet.domain.model.GitHubEvent
import com.github.masato1230.githubclienet.domain.model.GitHubRepositoryModel
import com.github.masato1230.githubclienet.presentation.components.error.CommunicationErrorView
import com.github.masato1230.githubclienet.presentation.screens.userdetail.components.UserDetailTopAppBar
import com.github.masato1230.githubclienet.presentation.screens.userdetail.listitems.UserDetailBaseListItem
import com.github.masato1230.githubclienet.presentation.screens.userdetail.listitems.UserDetailEventListItem
import com.github.masato1230.githubclienet.presentation.screens.userdetail.listitems.UserDetailRepositoriesListItem
import com.github.masato1230.githubclienet.presentation.screens.userdetail.listitems.UserDetailSectionErrorListItem
import com.github.masato1230.githubclienet.presentation.screens.userdetail.listitems.UserDetailTitleListItem
import com.github.masato1230.githubclienet.presentation.screens.userdetail.states.UserDetailListItemState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun UserDetailScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: UserDetailViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    onClickXAccount: (String) -> Unit,
    onClickBlogLink: (String) -> Unit,
    onClickRepository: (GitHubRepositoryModel) -> Unit,
    onClickEvent: (GitHubEvent) -> Unit,
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

        when (val state = viewModel.state.collectAsState().value) {
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
                        message = state.e.message,
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
                    onClickXAccount = onClickXAccount,
                    onClickBlogLink = onClickBlogLink,
                    onClickRepository = onClickRepository,
                    onClickEvent = onClickEvent,
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
    onClickXAccount: (String) -> Unit,
    onClickBlogLink: (String) -> Unit,
    onClickRepository: (GitHubRepositoryModel) -> Unit,
    onClickEvent: (GitHubEvent) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 80.dp),
    ) {
        items(
            listItems,
            key = { it.key }
        ) { listItem ->
            when (listItem) {
                is UserDetailListItemState.UserDetail -> {
                    UserDetailBaseListItem(
                        userDetail = listItem.userDetail,
                        onClickXAccount = onClickXAccount,
                        onClickBlogLink = onClickBlogLink,
                    )
                }

                is UserDetailListItemState.RepositorySectionTitle -> {
                    UserDetailTitleListItem(
                        title = stringResource(id = R.string.user_detail_repository),
                        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
                    )
                }

                is UserDetailListItemState.Repositories -> {
                    UserDetailRepositoriesListItem(
                        repositories = listItem.repositories,
                        onClickRepository = onClickRepository,
                    )
                }

                is UserDetailListItemState.EventSectionTitle -> {
                    UserDetailTitleListItem(
                        title = stringResource(id = R.string.user_detail_recent_activities),
                        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
                    )
                }

                is UserDetailListItemState.EventSectionEmpty -> {
                    Text(
                        text = stringResource(id = R.string.user_detail_no_recent_activities),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                    )
                }

                is UserDetailListItemState.Event -> {
                    UserDetailEventListItem(
                        event = listItem.event,
                        isLastEvent = listItem.isLastEvent,
                        onClick = onClickEvent,
                    )
                }

                is UserDetailListItemState.Error -> {
                    UserDetailSectionErrorListItem()
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