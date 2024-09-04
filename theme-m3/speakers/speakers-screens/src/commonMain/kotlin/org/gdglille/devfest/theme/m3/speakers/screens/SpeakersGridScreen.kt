package org.gdglille.devfest.theme.m3.speakers.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.paligot.confily.resources.Resource
import com.paligot.confily.resources.screen_speakers
import kotlinx.collections.immutable.ImmutableList
import org.gdglille.devfest.android.theme.m3.speakers.semantics.SpeakersSemantics
import org.gdglille.devfest.android.theme.m3.style.Scaffold
import org.gdglille.devfest.android.theme.m3.style.SpacingTokens
import org.gdglille.devfest.android.theme.m3.style.toDp
import org.gdglille.devfest.models.ui.SpeakerItemUi
import org.gdglille.devfest.theme.m3.style.placeholder.placeholder
import org.gdglille.devfest.theme.m3.style.speakers.items.LargeSpeakerItem
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpeakersGridScreen(
    speakers: ImmutableList<SpeakerItemUi>,
    onSpeakerClicked: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    isLoading: Boolean = false
) {
    Scaffold(
        title = stringResource(Resource.string.screen_speakers),
        modifier = modifier,
        hasScrollBehavior = false
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 130.dp),
            modifier = Modifier
                .testTag(SpeakersSemantics.list)
                .padding(top = it.calculateTopPadding())
                .fillMaxWidth(),
            state = state,
            verticalArrangement = Arrangement.spacedBy(SpacingTokens.MediumSpacing.toDp()),
            horizontalArrangement = Arrangement.spacedBy(SpacingTokens.MediumSpacing.toDp()),
            contentPadding = PaddingValues(
                vertical = SpacingTokens.ExtraLargeSpacing.toDp(),
                horizontal = SpacingTokens.MediumSpacing.toDp()
            ),
            content = {
                items(speakers.toList(), key = { it.id }) {
                    LargeSpeakerItem(
                        name = it.name,
                        description = it.company,
                        url = it.url,
                        onClick = { onSpeakerClicked(it.id) },
                        modifier = Modifier.placeholder(isLoading)
                    )
                }
            }
        )
    }
}
