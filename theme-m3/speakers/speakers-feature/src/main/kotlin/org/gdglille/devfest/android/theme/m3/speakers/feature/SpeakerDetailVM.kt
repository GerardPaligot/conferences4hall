package org.gdglille.devfest.android.theme.m3.speakers.feature

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gdglille.devfest.AlarmScheduler
import org.gdglille.devfest.android.theme.m3.style.R
import org.gdglille.devfest.repositories.AgendaRepository

@Composable
fun SpeakerDetailVM(
    speakerId: String,
    agendaRepository: AgendaRepository,
    alarmScheduler: AlarmScheduler,
    onTalkClicked: (id: String) -> Unit,
    onLinkClicked: (url: String) -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SpeakerDetailViewModel = viewModel(
        factory = SpeakerDetailViewModel.Factory.create(speakerId, agendaRepository, alarmScheduler)
    )
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value) {
        is SpeakerUiState.Loading -> SpeakerDetailOrientable(
            speaker = (uiState.value as SpeakerUiState.Loading).speaker,
            modifier = modifier,
            onTalkClicked = {},
            onFavoriteClicked = {},
            onLinkClicked = {},
            onBackClicked = onBackClicked
        )

        is SpeakerUiState.Failure -> Text(text = stringResource(id = R.string.text_error))
        is SpeakerUiState.Success -> SpeakerDetailOrientable(
            speaker = (uiState.value as SpeakerUiState.Success).speaker,
            modifier = modifier,
            onTalkClicked = onTalkClicked,
            onFavoriteClicked = {
                viewModel.markAsFavorite(context, it)
            },
            onLinkClicked = onLinkClicked,
            onBackClicked = onBackClicked
        )
    }
}
