package org.gdglille.devfest.android.theme.m3.networking.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.gdglille.devfest.android.theme.m3.style.Conferences4HallTheme
import org.gdglille.devfest.android.shared.resources.Resource
import org.gdglille.devfest.android.shared.resources.text_empty_networking
import org.gdglille.devfest.android.shared.resources.text_empty_networking_warning
import org.gdglille.devfest.android.shared.resources.text_here_we_go
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EmptyNetworkingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(text = stringResource(Resource.string.text_empty_networking))
        Text(text = stringResource(Resource.string.text_empty_networking_warning))
        Text(text = stringResource(Resource.string.text_here_we_go))
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyNetworkingScreenPreview() {
    Conferences4HallTheme {
        EmptyNetworkingScreen()
    }
}
