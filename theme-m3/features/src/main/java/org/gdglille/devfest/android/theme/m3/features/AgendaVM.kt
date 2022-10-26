package org.gdglille.devfest.android.theme.m3.features

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.gdglille.devfest.android.data.AlarmScheduler
import org.gdglille.devfest.android.data.viewmodels.AgendaUiState
import org.gdglille.devfest.android.data.viewmodels.AgendaViewModel
import org.gdglille.devfest.android.screens.Agenda
import org.gdglille.devfest.android.ui.resources.models.TabActionsUi
import org.gdglille.devfest.repositories.AgendaRepository

@ExperimentalPagerApi
@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun AgendaVM(
    tabs: TabActionsUi,
    agendaRepository: AgendaRepository,
    alarmScheduler: AlarmScheduler,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(),
    onTalkClicked: (id: String) -> Unit,
) {
    val context = LocalContext.current
    val count = tabs.tabActions.count()
    val viewModel: AgendaViewModel = viewModel(
        factory = AgendaViewModel.Factory.create(
            tabs.tabActions.map { it.route },
            agendaRepository,
            alarmScheduler
        )
    )
    val uiState = viewModel.uiState.collectAsState()
    HorizontalPager(
        count = if (count == 0) 1 else count,
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Top
    ) { page ->
        when (uiState.value) {
            is AgendaUiState.Loading -> Agenda(
                agenda = (uiState.value as AgendaUiState.Loading).agenda.first(),
                modifier = modifier,
                isLoading = true,
                onTalkClicked = {},
                onFavoriteClicked = { }
            )

            is AgendaUiState.Failure -> Text(text = stringResource(id = R.string.text_error))
            is AgendaUiState.Success -> Agenda(
                agenda = (uiState.value as AgendaUiState.Success).agenda[page],
                modifier = modifier,
                onTalkClicked = onTalkClicked,
                onFavoriteClicked = { talkItem ->
                    viewModel.markAsFavorite(context, talkItem)
                }
            )
        }
    }
}
