package org.gdglille.devfest.theme.m3.infos.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.paligot.confily.infos.ui.qanda.QAndAItem
import com.paligot.confily.models.ui.QuestionAndResponseUi
import kotlinx.collections.immutable.ImmutableList

@Composable
fun QAndAListScreen(
    qAndA: ImmutableList<QuestionAndResponseUi>,
    onExpandedClicked: (QuestionAndResponseUi) -> Unit,
    onLinkClicked: (url: String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(qAndA) { index, item ->
            Column {
                QAndAItem(
                    qAndA = item,
                    isLoading = isLoading,
                    onExpandedClicked = onExpandedClicked,
                    onLinkClicked = onLinkClicked
                )
                if (index != qAndA.size - 1) {
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.background,
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}
