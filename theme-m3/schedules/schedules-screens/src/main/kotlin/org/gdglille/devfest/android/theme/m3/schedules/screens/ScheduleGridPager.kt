package org.gdglille.devfest.android.theme.m3.schedules.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList
import org.gdglille.devfest.models.ui.AgendaUi
import org.gdglille.devfest.models.ui.TalkItemUi

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScheduleGridPager(
    agendas: ImmutableList<AgendaUi>,
    onTalkClicked: (id: String) -> Unit,
    onFavoriteClicked: (TalkItemUi) -> Unit,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(pageCount = { 0 }),
    isSmallSize: Boolean = false,
    isLoading: Boolean = false,
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Top
    ) { page ->
        ScheduleGridScreen(
            agenda = agendas[page],
            onTalkClicked = onTalkClicked,
            onFavoriteClicked = onFavoriteClicked,
            modifier = modifier,
            isSmallSize = isSmallSize,
            isLoading = isLoading
        )
    }
}
