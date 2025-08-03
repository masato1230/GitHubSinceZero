package com.github.masato1230.githubclienet.presentation.screens.userdetail

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
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

        LazyColumn(
            modifier = Modifier.padding(paddingValues),
        ) {
            item {
                Row {

                }
            }
        }
    }
}