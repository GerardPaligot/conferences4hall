package org.gdglille.devfest.android.screens.agenda

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.russhwolf.settings.ExperimentalSettingsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.gdglille.devfest.android.R
import org.gdglille.devfest.android.screens.Agenda
import org.gdglille.devfest.repositories.AgendaRepository

@ExperimentalCoroutinesApi
@ExperimentalSettingsApi
@FlowPreview
@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
@Composable
fun AgendaVM(
    agendaRepository: AgendaRepository,
    modifier: Modifier = Modifier,
    onTalkClicked: (id: String) -> Unit,
) {
    val context = LocalContext.current
    val viewModel: AgendaViewModel = viewModel(
        factory = AgendaViewModel.Factory.create(context, agendaRepository)
    )
    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value) {
        is AgendaUiState.Loading -> Agenda(
            agenda = (uiState.value as AgendaUiState.Loading).agenda,
            modifier = modifier,
            isLoading = true,
            onTalkClicked = {},
            onFavoriteClicked = { }
        )
        is AgendaUiState.Failure -> Text(text = stringResource(id = R.string.text_error))
        is AgendaUiState.Success -> Agenda(
            agenda = (uiState.value as AgendaUiState.Success).agenda,
            modifier = modifier,
            onTalkClicked = onTalkClicked,
            onFavoriteClicked = { talkItem ->
                viewModel.markAsFavorite(context, talkItem)
            }
        )
    }
}
