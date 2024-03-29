package org.gdglille.devfest.android.theme.m3.schedules.ui.schedule

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.gdglille.devfest.android.theme.m3.style.Conferences4HallTheme
import org.gdglille.devfest.android.shared.resources.Resource
import org.gdglille.devfest.android.shared.resources.text_openfeedback_not_started
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun OpenFeedbackNotStarted(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Text(
        text = stringResource(Resource.string.text_openfeedback_not_started),
        color = color,
        style = style,
        modifier = modifier
            .border(width = 1.dp, color = color)
            .padding(12.dp)
    )
}

@Preview
@Composable
private fun OpenFeedbackNotStartedPreview() {
    Conferences4HallTheme {
        OpenFeedbackNotStarted()
    }
}
