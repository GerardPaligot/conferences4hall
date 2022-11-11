package org.gdglille.devfest.android.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.openfeedback.android.OpenFeedbackConfig
import io.openfeedback.android.components.rememberOpenFeedbackState
import org.gdglille.devfest.android.TopActions
import org.gdglille.devfest.android.components.appbars.TopAppBar
import org.gdglille.devfest.android.components.speakers.SpeakerSection
import org.gdglille.devfest.android.components.talks.OpenFeedbackSection
import org.gdglille.devfest.android.components.talks.TalkSection
import org.gdglille.devfest.android.theme.Conferences4HallTheme
import org.gdglille.devfest.android.theme.m3.ui.R
import org.gdglille.devfest.android.ui.resources.models.TopActionsUi
import org.gdglille.devfest.models.TalkUi

@ExperimentalMaterial3Api
@Composable
fun ScheduleDetail(
    talk: TalkUi,
    openFeedbackState: OpenFeedbackConfig,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onSpeakerClicked: (id: String) -> Unit,
    onShareClicked: (text: String) -> Unit
) {
    val textShared = stringResource(id = R.string.input_share_talk, talk.title, talk.speakersSharing)
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.screen_schedule_detail),
                navigationIcon = { Back(onClick = onBackClicked) },
                actions = TopActionsUi(topActions = listOf(TopActions.share)),
                onActionClicked = {
                    when (it.id) {
                        TopActions.share.id -> {
                            onShareClicked(textShared)
                        }

                        else -> TODO()
                    }
                }
            )
        },
        content = {
            val contentPadding = 8.dp
            LazyColumn(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                contentPadding = it,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    TalkSection(talk = talk)
                }
                item {
                    if (!LocalInspectionMode.current) {
                        OpenFeedbackSection(
                            openFeedbackSessionId = talk.openFeedbackSessionId,
                            openFeedbackState = openFeedbackState,
                            canGiveFeedback = talk.canGiveFeedback
                        )
                    }
                }
                item {
                    SpeakerSection(
                        speakers = talk.speakers,
                        onSpeakerItemClick = onSpeakerClicked
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun TalkDetailPreview() {
    Conferences4HallTheme {
        ScheduleDetail(
            talk = TalkUi.fake,
            openFeedbackState = rememberOpenFeedbackState(
                projectId = "",
                firebaseConfig = OpenFeedbackConfig.FirebaseConfig(
                    projectId = "",
                    applicationId = "",
                    apiKey = "",
                    databaseUrl = ""
                )
            ),
            onBackClicked = {},
            onSpeakerClicked = {},
            onShareClicked = {}
        )
    }
}
