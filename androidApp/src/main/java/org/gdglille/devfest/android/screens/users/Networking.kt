package org.gdglille.devfest.android.screens.users

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gdglille.devfest.android.R
import org.gdglille.devfest.android.screens.Networking
import org.gdglille.devfest.repositories.UserRepository

@Composable
fun NetworkingVM(
    userRepository: UserRepository,
    modifier: Modifier = Modifier
) {
    val viewModel: NetworkingViewModel = viewModel(
        factory = NetworkingViewModel.Factory.create(userRepository)
    )
    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value) {
        is NetworkingUiState.Loading -> Text(text = stringResource(id = R.string.text_loading))
        is NetworkingUiState.Failure -> Text(text = stringResource(id = R.string.text_error))
        is NetworkingUiState.Success -> Networking(
            users = (uiState.value as NetworkingUiState.Success).users,
            modifier = modifier,
            onNetworkDeleted = { viewModel.deleteNetworking(it) }
        )
    }
}
