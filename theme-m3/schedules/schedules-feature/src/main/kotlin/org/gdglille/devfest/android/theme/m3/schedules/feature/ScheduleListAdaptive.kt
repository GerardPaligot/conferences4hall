package org.gdglille.devfest.android.theme.m3.schedules.feature

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.SupportingPaneScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@OptIn(
    ExperimentalMaterial3AdaptiveApi::class,
    ExperimentalCoroutinesApi::class,
    FlowPreview::class
)
@Composable
fun ScheduleListAdaptive(
    onScheduleStarted: () -> Unit,
    onFilterClicked: () -> Unit,
    onTalkClicked: (id: String) -> Unit,
    showFilterIcon: Boolean,
    showInVertical: Boolean,
    modifier: Modifier = Modifier,
    columnCount: Int = 1,
) {
    SupportingPaneScaffold(
        modifier = modifier,
        mainPane = {
            ScheduleListCompactVM(
                onScheduleStarted = onScheduleStarted,
                onFilterClicked = onFilterClicked,
                onTalkClicked = onTalkClicked,
                showFilterIcon = showFilterIcon,
                showInVertical = showInVertical,
                columnCount = columnCount
            )
        },
        supportingPane = {
            AgendaFiltersCompactVM()
        }
    )
}
