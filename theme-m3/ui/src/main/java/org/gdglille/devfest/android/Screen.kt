package org.gdglille.devfest.android

import androidx.annotation.StringRes
import org.gdglille.devfest.android.theme.m3.ui.R
import org.gdglille.devfest.android.ui.resources.actions.BottomAction
import org.gdglille.devfest.android.ui.resources.actions.FabAction
import org.gdglille.devfest.android.ui.resources.actions.TabAction
import org.gdglille.devfest.android.ui.resources.actions.TopAction

object ActionIds {
    const val SHARE_ID = 0
    const val SCAN_NETWORKING = 1
    const val SCAN_TICKET = 2
    const val CREATE_PROFILE = 3
    const val FAVORITE = 4
    const val REPORT = 5
}

object TopActions {
    val share = TopAction(
        id = ActionIds.SHARE_ID,
        icon = R.drawable.ic_mtrl_share_line,
        contentDescription = R.string.action_share_talk
    )
    val favorite = TopAction(
        id = ActionIds.FAVORITE,
        icon = R.drawable.ic_mtrl_star_line,
        contentDescription = R.string.action_filtering_favorites
    )
    val favoriteFilled = TopAction(
        id = ActionIds.FAVORITE,
        icon = R.drawable.ic_mtrl_star_fill,
        contentDescription = R.string.action_filtering_favorites
    )
    val qrCodeScanner = TopAction(
        id = ActionIds.SCAN_NETWORKING,
        icon = R.drawable.ic_mtrl_qr_code_scanner_line,
        contentDescription = R.string.action_qrcode_scanner
    )
    val qrCode = TopAction(
        id = ActionIds.CREATE_PROFILE,
        icon = R.drawable.ic_mtrl_qr_code_line,
        contentDescription = R.string.action_qrcode_generator
    )
    val report = TopAction(
        id = ActionIds.REPORT,
        icon = R.drawable.ic_mtrl_report_line,
        contentDescription = R.string.action_report
    )
}

object TabActions {
    val event = TabAction(route = Screen.Event.route, labelId = R.string.screen_event)
    val menus = TabAction(route = Screen.Menus.route, labelId = R.string.screen_menus)
    val qanda = TabAction(route = Screen.QAndA.route, labelId = R.string.screen_qanda)
    val coc = TabAction(route = Screen.CoC.route, labelId = R.string.screen_coc)
}

object FabActions {
    val scanTicket = FabAction(
        id = ActionIds.SCAN_TICKET,
        icon = R.drawable.ic_mtrl_qr_code_scanner_line,
        contentDescription = null,
        label = R.string.action_ticket_scanner
    )
}

object BottomActions {
    val agenda = BottomAction(
        route = Screen.Agenda.route,
        icon = R.drawable.ic_mtrl_event_line,
        iconSelected = R.drawable.ic_mtrl_event_fill,
        label = R.string.screen_agenda,
        contentDescription = null
    )
    val networking = BottomAction(
        route = Screen.Networking.route,
        icon = R.drawable.ic_mtrl_group_line,
        iconSelected = R.drawable.ic_mtrl_group_fill,
        label = R.string.screen_networking,
        contentDescription = null
    )
    val partners = BottomAction(
        route = Screen.Partners.route,
        icon = R.drawable.ic_mtrl_handshake_line,
        iconSelected = R.drawable.ic_mtrl_handshake_fill,
        label = R.string.screen_partners,
        contentDescription = null,
    )
    val info = BottomAction(
        route = Screen.Info.route,
        label = R.string.screen_info,
        icon = R.drawable.ic_mtrl_local_activity_line,
        iconSelected = R.drawable.ic_mtrl_local_activity_fill,
        contentDescription = null,
        selectedRoutes = listOf(Screen.Event.route, Screen.Menus.route, Screen.QAndA.route, Screen.CoC.route)
    )
}

sealed class Screen(
    val route: String,
    @StringRes val title: Int
) {
    object Agenda : Screen(route = "agenda", title = R.string.screen_agenda)
    object Networking : Screen(route = "networking", title = R.string.screen_networking)
    object Partners : Screen(route = "partners", title = R.string.screen_partners)
    object Info : Screen(route = "info", title = R.string.screen_info)
    object Event : Screen(route = "event", title = R.string.screen_info)
    object Menus : Screen(route = "menus", title = R.string.screen_info)
    object QAndA : Screen(route = "qanda", title = R.string.screen_info)
    object CoC : Screen(route = "coc", title = R.string.screen_info)
}