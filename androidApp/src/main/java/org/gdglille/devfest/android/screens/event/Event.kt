package org.gdglille.devfest.android.screens.event

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gdglille.devfest.android.R
import org.gdglille.devfest.android.screens.Event
import org.gdglille.devfest.repositories.AgendaRepository

@Composable
fun EventVM(
    agendaRepository: AgendaRepository,
    modifier: Modifier = Modifier,
    onFaqClick: (url: String) -> Unit,
    onCoCClick: (url: String) -> Unit,
    onTicketScannerClicked: () -> Unit,
    onMenusClicked: () -> Unit,
    onTwitterClick: (url: String?) -> Unit,
    onLinkedInClick: (url: String?) -> Unit
) {
    val viewModel: EventViewModel = viewModel(
        factory = EventViewModel.Factory.create(agendaRepository)
    )
    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value) {
        is EventUiState.Loading -> Event(
            logo = R.drawable.ic_launcher_foreground,
            event = (uiState.value as EventUiState.Loading).event,
            modifier = modifier,
            isLoading = true,
            onFaqClick = {},
            onCoCClick = {},
            onTicketScannerClicked = {},
            onMenusClicked = {},
            onTwitterClick = {}
        ) {}
        is EventUiState.Failure -> Text(text = stringResource(id = R.string.text_error))
        is EventUiState.Success -> Event(
            logo = R.drawable.ic_launcher_foreground,
            event = (uiState.value as EventUiState.Success).event,
            modifier = modifier,
            onFaqClick = onFaqClick,
            onCoCClick = onCoCClick,
            onTicketScannerClicked = onTicketScannerClicked,
            onMenusClicked = onMenusClicked,
            onTwitterClick = onTwitterClick,
            onLinkedInClick = onLinkedInClick
        )
    }
}
