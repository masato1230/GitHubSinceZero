package com.github.masato1230.githubclienet.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun HomeScreen() {
    Scaffold { paddingValues ->
        // TODO error and loading
        HomeContent(
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
private fun HomeContent(modifier: Modifier) {
    Text(
        "home",
        modifier = modifier,
    )
}