package org.gdglille.devfest.android.theme.m3.infos.ui.tickets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.gdglille.devfest.android.shared.resources.Resource
import org.gdglille.devfest.android.shared.resources.semantic_ticket_qrcode
import org.gdglille.devfest.android.theme.m3.style.placeholder.placeholder
import org.gdglille.devfest.models.ui.Image
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

private const val TicketRatio = 3 / 4

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TicketQrCode(
    qrCode: Image,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    shape: Shape = RoundedCornerShape(16.dp),
    elevation: Dp = 8.dp
) {
    Surface(
        modifier = modifier.wrapContentHeight(),
        shape = shape,
        tonalElevation = elevation
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 28.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                bitmap = qrCode.asImageBitmap(),
                contentDescription = stringResource(Resource.string.semantic_ticket_qrcode),
                modifier = Modifier
                    .size(this.maxWidth * TicketRatio)
                    .placeholder(isLoading)
            )
        }
    }
}
