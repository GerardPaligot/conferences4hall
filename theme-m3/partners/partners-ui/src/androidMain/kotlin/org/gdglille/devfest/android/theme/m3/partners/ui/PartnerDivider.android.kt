package org.gdglille.devfest.android.theme.m3.partners.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.gdglille.devfest.android.theme.m3.style.Conferences4HallTheme
import org.gdglille.devfest.theme.m3.partners.ui.PartnerDivider

@Preview
@Composable
private fun PartnerDividerPreview() {
    Conferences4HallTheme {
        PartnerDivider(title = "Gold")
    }
}