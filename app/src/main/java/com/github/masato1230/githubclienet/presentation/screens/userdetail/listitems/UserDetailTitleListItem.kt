package com.github.masato1230.githubclienet.presentation.screens.userdetail.listitems

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun UserDetailTitleListItem(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier.padding(horizontal = 20.dp),
    )
}