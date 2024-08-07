package org.gdglille.devfest.android.theme.m3.infos.ui.tickets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.gdglille.devfest.android.shared.resources.Resource
import org.gdglille.devfest.android.shared.resources.semantic_ticket_id
import org.gdglille.devfest.android.shared.resources.semantic_ticket_qrcode
import org.gdglille.devfest.android.shared.resources.text_ticket_firstname
import org.gdglille.devfest.android.shared.resources.text_ticket_lastname
import org.gdglille.devfest.android.theme.m3.style.Conferences4HallTheme
import org.gdglille.devfest.android.theme.m3.style.placeholder.placeholder
import org.gdglille.devfest.android.theme.m3.style.shapes.DottedShape
import org.gdglille.devfest.models.ui.Image
import org.gdglille.devfest.models.ui.TicketInfoUi
import org.gdglille.devfest.models.ui.TicketUi
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

private const val TicketRatio = 2 / 3

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TicketDetailed(
    ticket: TicketInfoUi,
    qrCode: Image?,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    shape: Shape = RoundedCornerShape(16.dp),
    elevation: Dp = 8.dp
) {
    val cdTicketId = stringResource(Resource.string.semantic_ticket_id, ticket.id)
    Surface(
        modifier = modifier.wrapContentHeight(),
        shape = shape,
        tonalElevation = elevation
    ) {
        Column {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 28.dp)
            ) {
                Row(
                    modifier = Modifier
                        .semantics(mergeDescendants = true) {}
                        .placeholder(isLoading)
                ) {
                    Text(
                        text = stringResource(Resource.string.text_ticket_firstname),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = ticket.firstName,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier
                        .semantics(mergeDescendants = true) {}
                        .placeholder(isLoading)
                ) {
                    Text(
                        text = stringResource(Resource.string.text_ticket_lastname),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = ticket.lastName,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = LocalContentColor.current, shape = DottedShape(10.dp))
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(vertical = 24.dp, horizontal = 28.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = ticket.id,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .semantics { text = AnnotatedString(cdTicketId) }
                        .placeholder(isLoading)
                )
                if (qrCode != null) {
                    Image(
                        bitmap = qrCode.asImageBitmap(),
                        contentDescription = stringResource(
                            Resource.string.semantic_ticket_qrcode
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .placeholder(isLoading)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TicketPreview() {
    Conferences4HallTheme {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TicketDetailed(
                ticket = TicketUi.fake.info!!,
                qrCode = TicketUi.fake.qrCode,
                modifier = Modifier.width(maxWidth)
            )
        }
    }
}

@Preview
@Composable
private fun TicketPreviewLongText() {
    Conferences4HallTheme {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TicketDetailed(
                ticket = TicketUi.fake.info!!,
                qrCode = TicketUi.fake.qrCode,
                modifier = Modifier.width(maxWidth)
            )
        }
    }
}
