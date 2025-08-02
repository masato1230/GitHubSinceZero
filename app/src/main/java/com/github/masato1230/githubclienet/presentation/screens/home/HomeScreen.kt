package com.github.masato1230.githubclienet.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun HomeScreen(
    viewModel: HiltViewModel = hiltViewModel(),
) {
    Scaffold { paddingValues ->
        // TODO error and loading
        HomeContent(
            sampleText = viewModel.sampleText.value,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
private fun HomeContent(
    sampleText: String,
    modifier: Modifier,
    ) {
    Text(
        sampleText,
        modifier = modifier,
    )
}