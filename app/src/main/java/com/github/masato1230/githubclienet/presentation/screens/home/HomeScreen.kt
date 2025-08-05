package com.github.masato1230.githubclienet.presentation.screens.home

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.github.masato1230.githubclienet.domain.model.GitHubUser
import com.github.masato1230.githubclienet.presentation.components.error.CommunicationErrorView
import com.github.masato1230.githubclienet.presentation.screens.home.components.HomeGitHubUserListItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onClickUser: (GitHubUser) -> Unit,
    viewModel: HiltViewModel = hiltViewModel(),
) {
    val pagingUsers = viewModel.users.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(text = "GitHub Users")
                },
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
    ) { paddingValues ->
        when (val refreshState =pagingUsers.loadState.refresh) {
            is LoadState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is LoadState.Error -> {
                LaunchedEffect(
                    Unit
                ) {
                    Log.e(
                        "HomeScreen",
                        "Error",
                        (pagingUsers.loadState.refresh as LoadState.Error).error
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    CommunicationErrorView(
                        message = refreshState.error.message,
                        onRetry = pagingUsers::retry,
                        modifier = Modifier
                            .padding(16.dp),
                    )
                }
            }

            is LoadState.NotLoading -> {
                HomeContent(
                    pagingUsers = pagingUsers,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope,
                    onClickUser = onClickUser,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun HomeContent(
    pagingUsers: LazyPagingItems<GitHubUser>,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onClickUser: (GitHubUser) -> Unit,
    modifier: Modifier,
) {
    val navigationBarPaddings = WindowInsets.navigationBars.asPaddingValues()
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = navigationBarPaddings.calculateTopPadding(),
            bottom = navigationBarPaddings.calculateBottomPadding() + 80.dp,
        ),
    ) {
        items(
            pagingUsers.itemCount,
            key = pagingUsers.itemKey { it.id },
        ) {
            val user = pagingUsers[it] ?: return@items
            HomeGitHubUserListItem(
                user = user,
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope,
                onClick = { onClickUser(user) },
            )
        }
        item(
            key = "append_loading",
        ) {
            when (val appendState = pagingUsers.loadState.append) {
                is LoadState.Error -> {
                    CommunicationErrorView(
                        message = appendState.error.message,
                        onRetry = { pagingUsers.retry() },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp),
                    )
                }

                is LoadState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }

                is LoadState.NotLoading -> Unit
            }
        }
    }
}