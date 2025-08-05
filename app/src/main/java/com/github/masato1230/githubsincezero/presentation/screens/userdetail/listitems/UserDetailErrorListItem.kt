package com.github.masato1230.githubsincezero.presentation.screens.userdetail.listitems

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.masato1230.githubsincezero.R

@Preview
@Composable
internal fun UserDetailSectionErrorListItem(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(
            id = R.string.communication_failed,
        ),
        style = MaterialTheme.typography.labelLarge.copy(
            color = MaterialTheme.colorScheme.error,
        ),
        modifier = modifier.padding(horizontal = 20.dp),
    )
}