package org.gdglille.devfest.android.theme.m3.schedules.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.gdglille.devfest.android.theme.m3.schedules.semantics.SchedulesSemantics
import org.gdglille.devfest.android.theme.m3.schedules.ui.talks.MediumScheduleItem
import org.gdglille.devfest.android.theme.m3.schedules.ui.talks.NoFavoriteTalks
import org.gdglille.devfest.android.theme.m3.schedules.ui.talks.SmallScheduleItem
import org.gdglille.devfest.android.theme.m3.schedules.ui.talks.Time
import org.gdglille.devfest.android.theme.m3.style.Conferences4HallTheme
import org.gdglille.devfest.android.theme.m3.style.SpacingTokens
import org.gdglille.devfest.android.theme.m3.style.placeholder.placeholder
import org.gdglille.devfest.android.theme.m3.style.previews.PHONE_LANDSCAPE
import org.gdglille.devfest.android.theme.m3.style.schedules.findTimeImageVector
import org.gdglille.devfest.android.theme.m3.style.schedules.pause.MediumPauseItem
import org.gdglille.devfest.android.theme.m3.style.schedules.pause.SmallPauseItem
import org.gdglille.devfest.android.theme.m3.style.toDp
import org.gdglille.devfest.models.ui.AgendaUi
import org.gdglille.devfest.models.ui.EventSessionItemUi
import org.gdglille.devfest.models.ui.TalkItemUi
import kotlin.math.floor

const val NbHorizontalPadding = 2

@Composable
fun ScheduleGridScreen(
    agenda: AgendaUi,
    onTalkClicked: (id: String) -> Unit,
    onEventSessionClicked: (id: String) -> Unit,
    onFavoriteClicked: (TalkItemUi) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    isSmallSize: Boolean = false,
    isLoading: Boolean = false
) {
    if (agenda.onlyFavorites && !isLoading && agenda.sessions.keys.isEmpty()) {
        NoFavoriteTalks(modifier = modifier)
    } else {
        BoxWithConstraints(modifier = modifier) {
            val minSize = 260.dp
            val mediumSpacing = SpacingTokens.MediumSpacing.toDp()
            val widthSize = this.maxWidth - (mediumSpacing * NbHorizontalPadding)
            val count = floor(widthSize / minSize).toInt()
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = minSize),
                modifier = Modifier.testTag(SchedulesSemantics.list),
                state = state,
                contentPadding = PaddingValues(
                    vertical = SpacingTokens.LargeSpacing.toDp(),
                    horizontal = SpacingTokens.MediumSpacing.toDp()
                ),
                verticalArrangement = Arrangement.spacedBy(mediumSpacing),
                horizontalArrangement = Arrangement.spacedBy(mediumSpacing)
            ) {
                agenda.sessions.entries.forEach { slot ->
                    item(span = { GridItemSpan(count) }) {
                        Time(time = slot.key, modifier = Modifier.placeholder(visible = isLoading))
                    }
                    items(slot.value.toList(), key = { it.id }) {
                        when (it) {
                            is EventSessionItemUi -> {
                                if (isSmallSize) {
                                    SmallPauseItem(
                                        title = it.title,
                                        room = it.room,
                                        time = it.time,
                                        timeImageVector = it.timeInMinutes.findTimeImageVector(),
                                        modifier = Modifier.placeholder(visible = isLoading)
                                    )
                                } else {
                                    MediumPauseItem(
                                        title = it.title,
                                        room = it.room,
                                        time = it.time,
                                        timeImageVector = it.timeInMinutes.findTimeImageVector(),
                                        modifier = Modifier.placeholder(visible = isLoading),
                                        onClick = if (it.description != null) {
                                            {
                                                onEventSessionClicked(it.id)
                                            }
                                        } else {
                                            null
                                        }
                                    )
                                }
                            }

                            is TalkItemUi -> {
                                if (isSmallSize) {
                                    SmallScheduleItem(
                                        talk = it,
                                        modifier = Modifier.placeholder(visible = isLoading),
                                        onFavoriteClicked = onFavoriteClicked,
                                        onTalkClicked = onTalkClicked
                                    )
                                } else {
                                    MediumScheduleItem(
                                        talk = it,
                                        modifier = Modifier.placeholder(visible = isLoading),
                                        onFavoriteClicked = onFavoriteClicked,
                                        onTalkClicked = onTalkClicked
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun ScheduleListScreenPreview() {
    Conferences4HallTheme {
        Scaffold {
            ScheduleGridScreen(
                agenda = AgendaUi.fake,
                onTalkClicked = {},
                onEventSessionClicked = {},
                onFavoriteClicked = { }
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(device = PHONE_LANDSCAPE)
@Composable
private fun ScheduleListScreenLandscapePreview() {
    Conferences4HallTheme {
        Scaffold {
            ScheduleGridScreen(
                agenda = AgendaUi.fake,
                onTalkClicked = {},
                onEventSessionClicked = {},
                onFavoriteClicked = { }
            )
        }
    }
}
