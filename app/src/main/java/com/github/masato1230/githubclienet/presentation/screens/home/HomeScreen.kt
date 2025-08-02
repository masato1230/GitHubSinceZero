package com.github.masato1230.githubclienet.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.masato1230.githubclienet.domain.model.GitHubUser

@Composable
internal fun HomeScreen(
    viewModel: HiltViewModel = hiltViewModel(),
) {
    Scaffold { paddingValues ->
        // TODO error and loading
        HomeContent(
            users = viewModel.users.value,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
private fun HomeContent(
    users: List<GitHubUser>,
    modifier: Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(
            users,
            key = { it.id },
        ) {
            Text(
                text = it.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        }
    }
}