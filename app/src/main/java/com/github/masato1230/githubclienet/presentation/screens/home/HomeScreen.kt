package com.github.masato1230.githubclienet.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.github.masato1230.githubclienet.domain.model.GitHubUser
import com.github.masato1230.githubclienet.presentation.screens.home.components.HomeGitHubUserListItem

@Composable
internal fun HomeScreen(
    viewModel: HiltViewModel = hiltViewModel(),
) {
    val pagingUsers = viewModel.users.collectAsLazyPagingItems()

    Scaffold { paddingValues ->
        when (pagingUsers.loadState.refresh) {
            is LoadState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is LoadState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = "Error")
                }
            }

            is LoadState.NotLoading -> {
                HomeContent(
                    pagingUsers = pagingUsers,
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    pagingUsers: LazyPagingItems<GitHubUser>,
    modifier: Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 80.dp),
    ) {
        items(
            pagingUsers.itemCount,
            key = pagingUsers.itemKey { it.id },
        ) {
            val user = pagingUsers[it] ?: return@items
            HomeGitHubUserListItem(user = user)
        }
        item(
            key = "append_loading",
        ) {
            when (pagingUsers.loadState.append) {
                is LoadState.Error -> {
                    // TODO: retry
                    val error = pagingUsers.loadState.append as LoadState.Error
                    Text(
                        text = "Error: ${error.error}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    )
                }

                is LoadState.Loading -> {
                    LinearProgressIndicator()
                }

                is LoadState.NotLoading -> Unit
            }
        }
    }
}