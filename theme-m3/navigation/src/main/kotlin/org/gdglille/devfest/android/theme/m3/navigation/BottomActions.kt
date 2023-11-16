package org.gdglille.devfest.android.theme.m3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.InterpreterMode
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material.icons.outlined.Hub
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.InterpreterMode
import org.gdglille.devfest.android.theme.m3.style.R
import org.gdglille.devfest.android.theme.m3.style.actions.NavigationAction

object BottomActions {
    val agenda = NavigationAction(
        route = Screen.ScheduleList.route,
        icon = Icons.Outlined.Event,
        iconSelected = Icons.Filled.Event,
        label = R.string.screen_agenda,
        contentDescription = null
    )
    val speakers = NavigationAction(
        route = Screen.SpeakerList.route,
        icon = Icons.Outlined.InterpreterMode,
        iconSelected = Icons.Filled.InterpreterMode,
        label = R.string.screen_speakers,
        contentDescription = null,
    )
    val myProfile = NavigationAction(
        route = Screen.MyProfile.route,
        icon = Icons.Outlined.Hub,
        iconSelected = Icons.Filled.Hub,
        label = R.string.screen_networking,
        contentDescription = null
    )
    val partners = NavigationAction(
        route = Screen.PartnerList.route,
        icon = Icons.Outlined.Handshake,
        iconSelected = Icons.Filled.Handshake,
        label = R.string.screen_partners,
        contentDescription = null,
    )
    val event = NavigationAction(
        route = Screen.Event.route,
        icon = Icons.Outlined.Info,
        iconSelected = Icons.Filled.Info,
        label = R.string.screen_info,
        contentDescription = null
    )
}
