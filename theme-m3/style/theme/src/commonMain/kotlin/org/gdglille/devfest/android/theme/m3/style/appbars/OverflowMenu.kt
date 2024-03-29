package org.gdglille.devfest.android.theme.m3.style.appbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.gdglille.devfest.android.shared.resources.Resource
import org.gdglille.devfest.android.shared.resources.action_expanded_menu
import org.gdglille.devfest.android.theme.m3.style.actions.TopAction
import org.gdglille.devfest.android.theme.m3.style.actions.TopActionsUi
import org.gdglille.devfest.android.theme.m3.style.menus.Dropdown
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun OverflowMenu(
    topActionsUi: TopActionsUi,
    modifier: Modifier = Modifier,
    onClick: (TopAction) -> Unit
) {
    val expanded: MutableState<Boolean> = remember { mutableStateOf(false) }
    Dropdown(
        expanded = expanded,
        modifier = modifier,
        onDismissRequest = { expanded.value = false },
        anchor = {
            IconButton(onClick = { expanded.value = true }) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = stringResource(Resource.string.action_expanded_menu)
                )
            }
        },
        children = {
            topActionsUi.actions.forEach {
                DropdownMenuItem(
                    text = { it.contentDescription?.let { Text(text = stringResource(it)) } },
                    onClick = {
                        onClick(it)
                        expanded.value = false
                    }
                )
            }
        }
    )
}
