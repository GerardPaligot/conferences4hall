package org.gdglille.devfest.android.theme.m3.schedules.ui.talks

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import org.gdglille.devfest.android.theme.m3.style.Conferences4HallTheme

@Composable
fun Time(
    time: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineSmall,
    color: Color = MaterialTheme.colorScheme.secondary
) {
    Text(
        text = time,
        modifier = modifier,
        style = style,
        color = color
    )
}

@Preview
@Composable
private fun TimePreview() {
    Conferences4HallTheme {
        Time(time = "10:00")
    }
}
