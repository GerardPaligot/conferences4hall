package org.gdglille.devfest.android.theme.m3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.PowerOff
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Upgrade
import org.gdglille.devfest.android.shared.resources.Resource
import org.gdglille.devfest.android.shared.resources.action_export
import org.gdglille.devfest.android.shared.resources.action_filtering
import org.gdglille.devfest.android.shared.resources.action_power_off
import org.gdglille.devfest.android.shared.resources.action_share_talk
import org.gdglille.devfest.android.theme.m3.style.actions.TopAction
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
object TopActions {
    val share = TopAction(
        id = ActionIds.SHARE_ID,
        icon = Icons.Outlined.Share,
        contentDescription = Resource.string.action_share_talk
    )
    val filters = TopAction(
        id = ActionIds.FILTERS,
        icon = Icons.Outlined.FilterList,
        contentDescription = Resource.string.action_filtering
    )
    val filtersFilled = TopAction(
        id = ActionIds.FILTERS,
        icon = Icons.Filled.FilterList,
        contentDescription = Resource.string.action_filtering
    )
    val disconnect = TopAction(
        id = ActionIds.DISCONNECT,
        icon = Icons.Outlined.PowerOff,
        contentDescription = Resource.string.action_power_off
    )
    val export = TopAction(
        id = ActionIds.EXPORT,
        icon = Icons.Outlined.Upgrade,
        contentDescription = Resource.string.action_export
    )
}
