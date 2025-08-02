package com.github.masato1230.githubclienet.presentation.components.error

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.masato1230.githubclienet.R
import com.github.masato1230.githubclienet.presentation.theme.GitHubClienetTheme

@Composable
fun CommunicationErrorView(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.communication_failed),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = onRetry,
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Preview
@Composable
private fun PreviewCommunicationErrorView() {
    GitHubClienetTheme {
        CommunicationErrorView(
            onRetry = {},
            modifier = Modifier.padding(horizontal = 16.dp),
        )
    }
}
