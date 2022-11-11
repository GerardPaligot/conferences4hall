package org.gdglille.devfest.android.components.speakers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.gdglille.devfest.android.theme.Conferences4HallTheme
import org.gdglille.devfest.android.theme.m3.ui.R
import org.gdglille.devfest.models.TalkItemUi

private const val MaxSpeakersCount = 3
private const val SpacingRatio = 5

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HorizontalSpeakersList(
    names: List<String>,
    avatars: List<String>,
    modifier: Modifier = Modifier,
    height: Dp = 25.dp,
    betweenSpacing: Dp = height / SpacingRatio
) {
    val count = (names.size - MaxSpeakersCount).coerceAtLeast(minimumValue = 0)
    val speakers = names.take(MaxSpeakersCount).joinToString(", ")
    Row(
        modifier = modifier.height(height),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SpeakersAvatar(
            speakersName = names.take(MaxSpeakersCount),
            speakersAvatar = avatars.take(MaxSpeakersCount),
            betweenSpacing = betweenSpacing
        )
        Text(
            text = if (count == 0) speakers else pluralStringResource(
                id = R.plurals.text_speakers_list,
                count = count,
                speakers,
                count
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HorizontalSpeakersListPreview() {
    Conferences4HallTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            HorizontalSpeakersList(
                names = TalkItemUi.fake.speakers,
                avatars = TalkItemUi.fake.speakersAvatar,
                height = 25.dp
            )
            HorizontalSpeakersList(
                names = TalkItemUi.fake.speakers + TalkItemUi.fake.speakers,
                avatars = TalkItemUi.fake.speakersAvatar + TalkItemUi.fake.speakersAvatar,
                height = 40.dp
            )
            HorizontalSpeakersList(
                names = TalkItemUi.fake.speakers + TalkItemUi.fake.speakers + TalkItemUi.fake.speakers,
                avatars = TalkItemUi.fake.speakersAvatar,
                height = 60.dp
            )
        }
    }
}
