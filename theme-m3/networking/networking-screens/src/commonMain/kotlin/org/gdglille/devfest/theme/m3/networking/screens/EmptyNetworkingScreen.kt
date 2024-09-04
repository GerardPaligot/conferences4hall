package org.gdglille.devfest.theme.m3.networking.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paligot.confily.resources.Resource
import com.paligot.confily.resources.text_empty_networking
import com.paligot.confily.resources.text_empty_networking_warning
import com.paligot.confily.resources.text_here_we_go
import org.jetbrains.compose.resources.stringResource

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
