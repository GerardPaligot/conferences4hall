@file:OptIn(ExperimentalResourceApi::class)

package org.gdglille.devfest.android.theme.m3.events.feature

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import org.gdglille.devfest.android.theme.m3.events.ui.EventItem
import org.gdglille.devfest.android.theme.m3.navigation.TabActions
import org.gdglille.devfest.android.theme.m3.style.Conferences4HallTheme
import org.gdglille.devfest.android.shared.resources.Resource
import org.gdglille.devfest.android.theme.m3.style.Scaffold
import org.gdglille.devfest.android.theme.m3.style.actions.TabActionsUi
import org.gdglille.devfest.android.shared.resources.screen_events
import org.gdglille.devfest.models.ui.EventItemListUi
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventList(
    events: EventItemListUi,
    onEventClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val tabActions = remember {
        TabActionsUi(actions = persistentListOf(TabActions.futureEvents, TabActions.pastEvents))
    }
    val pagerState = rememberPagerState(pageCount = { tabActions.actions.count() })
    Scaffold(
        title = stringResource(Resource.string.screen_events),
        tabActions = tabActions,
        pagerState = pagerState
    ) { padding ->
        HorizontalPager(state = pagerState) { page ->
            val items = if (page == 0) events.future else events.past
            LazyColumn(
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {
                items(items, key = { it.id }) { item ->
                    EventItem(
                        item = item,
                        isLoading = isLoading,
                        onClick = onEventClicked,
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EventListPreview() {
    Conferences4HallTheme {
        EventList(events = EventItemListUi.fake, onEventClicked = {})
    }
}
