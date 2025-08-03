package com.github.masato1230.githubclienet.presentation.screens.userdetail.sections.comopnents

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.masato1230.githubclienet.R

@Preview
@Composable
internal fun UserDetailSectionErrorText(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(
            id = R.string.communication_failed,
        ),
        style = MaterialTheme.typography.labelLarge.copy(
            color = MaterialTheme.colorScheme.error,
        ),
        modifier = modifier,
    )
}