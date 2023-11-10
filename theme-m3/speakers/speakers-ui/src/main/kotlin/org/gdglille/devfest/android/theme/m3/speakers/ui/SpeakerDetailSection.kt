package org.gdglille.devfest.android.theme.m3.speakers.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.gdglille.devfest.android.theme.m3.style.Conferences4HallTheme
import org.gdglille.devfest.android.theme.m3.style.markdowns.MarkdownText
import org.gdglille.devfest.android.theme.m3.style.placeholder
import org.gdglille.devfest.android.theme.m3.style.previews.ThemedPreviews
import org.gdglille.devfest.android.theme.m3.style.socials.SocialsSection
import org.gdglille.devfest.android.theme.m3.style.speakers.avatar.MediumSpeakerAvatar
import org.gdglille.devfest.models.ui.SpeakerUi

@Composable
fun SpeakerDetailSectionVertical(
    speaker: SpeakerUi,
    onLinkClicked: (url: String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    displayAvatar: Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        if (displayAvatar) {
            MediumSpeakerAvatar(
                url = speaker.url,
                contentDescription = null,
                modifier = Modifier.placeholder(visible = isLoading)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        SocialsSection(
            title = speaker.name,
            pronouns = speaker.pronouns,
            subtitle = speaker.activity,
            isLoading = isLoading,
            twitterUrl = speaker.twitterUrl,
            mastodonUrl = speaker.mastodonUrl,
            githubUrl = speaker.githubUrl,
            linkedinUrl = speaker.linkedinUrl,
            websiteUrl = speaker.websiteUrl,
            onLinkClicked = onLinkClicked
        )
        Spacer(modifier = Modifier.height(8.dp))
        MarkdownText(
            text = speaker.bio,
            modifier = Modifier.placeholder(visible = isLoading)
        )
    }
}

@Suppress("UnusedPrivateMember")
@ThemedPreviews
@Composable
private fun SpeakerDetailSectionVerticalPreview() {
    Conferences4HallTheme {
        SpeakerDetailSectionVertical(
            speaker = SpeakerUi.fake,
            onLinkClicked = {}
        )
    }
}
