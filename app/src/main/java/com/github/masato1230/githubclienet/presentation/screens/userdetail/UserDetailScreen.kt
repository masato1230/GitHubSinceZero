package com.github.masato1230.githubclienet.presentation.screens.userdetail

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.masato1230.githubclienet.domain.model.GitHubUserSection
import com.github.masato1230.githubclienet.presentation.components.error.CommunicationErrorView
import com.github.masato1230.githubclienet.presentation.screens.userdetail.components.UserDetailTopAppBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun UserDetailScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: UserDetailViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
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
                    sections = state.sections,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }
    }
}

@Composable
private fun UserDetailContent(
    sections: List<GitHubUserSection>,
    modifier: Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(sections) { section ->
            when (section) {
                is GitHubUserSection.BaseSection -> {
                    Text("base")
                }
            }
        }
    }
}