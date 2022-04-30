package org.gdglille.devfest.android.components.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.gdglille.devfest.android.theme.Conferences4HallTheme
import org.gdglille.devfest.models.EventUi
import org.gdglille.devfest.models.PartnerItemUi

@Composable
fun PartnerRow(
    partners: List<PartnerItemUi>,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    maxItems: Int = 3,
    onPartnerClick: (siteUrl: String?) -> Unit,
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val width = (this.maxWidth - (8.dp * (maxItems - 1))) / maxItems
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            partners.forEach {
                PartnerItem(
                    partnerUi = it,
                    modifier = Modifier.width(width).aspectRatio(1f),
                    isLoading = isLoading,
                    onClick = onPartnerClick
                )
            }
        }
    }
}

@Preview
@Composable
fun Eventreview() {
    Conferences4HallTheme {
        PartnerRow(
            partners = EventUi.fake.partners.silvers.chunked(3)[0],
            onPartnerClick = {}
        )
    }
}