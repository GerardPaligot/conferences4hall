package org.gdglille.devfest.android.theme.m3.style.speakers.avatar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import org.gdglille.devfest.android.theme.m3.style.Conferences4HallTheme

@Composable
fun SmallBorderedSpeakerAvatar(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    border: BorderStroke = SpeakerAvatarDefaults.borderedSmall,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape = SpeakerAvatarDefaults.borderedSmallShape
) {
    SpeakerAvatar(
        url = url,
        contentDescription = contentDescription,
        modifier = modifier.size(
            width = BorderedSpeakerAvatarSmallTokens.ContainerWidth,
            height = BorderedSpeakerAvatarSmallTokens.ContainerHeight
        ),
        contentScale = contentScale,
        border = border,
        shape = shape
    )
}

@Composable
fun MediumBorderedSpeakerAvatar(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    border: BorderStroke = SpeakerAvatarDefaults.borderedMedium,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape = SpeakerAvatarDefaults.borderedMediumShape
) {
    SpeakerAvatar(
        url = url,
        contentDescription = contentDescription,
        modifier = modifier.size(
            width = BorderedSpeakerAvatarMediumTokens.ContainerWidth,
            height = BorderedSpeakerAvatarMediumTokens.ContainerHeight
        ),
        contentScale = contentScale,
        border = border,
        shape = shape
    )
}

@Preview
@Composable
private fun SmallBorderedSpeakerAvatarPreview() {
    Conferences4HallTheme {
        Column {
            SmallBorderedSpeakerAvatar(
                url = "",
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun MediumBorderedSpeakerAvatarPreview() {
    Conferences4HallTheme {
        Column {
            MediumBorderedSpeakerAvatar(
                url = "",
                contentDescription = null
            )
        }
    }
}
