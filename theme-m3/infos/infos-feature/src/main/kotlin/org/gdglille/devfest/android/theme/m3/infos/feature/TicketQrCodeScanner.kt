package org.gdglille.devfest.android.theme.m3.infos.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.gdglille.devfest.android.theme.m3.style.appbars.TopAppBar
import org.gdglille.devfest.android.theme.m3.style.permissions.FeatureThatRequiresCameraPermission
import org.gdglille.devfest.android.theme.m3.infos.ui.tickets.TicketCameraPreview
import org.gdglille.devfest.android.shared.resources.Resource
import org.gdglille.devfest.android.shared.resources.screen_ticket_qrcode_scanner
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun TicketQrCodeScanner(
    navigateToSettingsScreen: () -> Unit,
    onQrCodeDetected: (String) -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = stringResource(Resource.string.screen_ticket_qrcode_scanner),
                navigationIcon = { Back(onClick = onBackClicked) }
            )
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                FeatureThatRequiresCameraPermission(
                    navigateToSettingsScreen = navigateToSettingsScreen,
                    onRefusePermissionClicked = onBackClicked,
                    content = {
                        TicketCameraPreview(onQrCodeDetected = { barcode ->
                            onQrCodeDetected(barcode.first())
                        })
                    }
                )
            }
        }
    )
}
