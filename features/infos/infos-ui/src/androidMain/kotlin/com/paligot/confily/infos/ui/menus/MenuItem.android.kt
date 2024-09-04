package com.paligot.confily.infos.ui.menus

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.paligot.confily.models.ui.MenuItemUi
import com.paligot.confily.style.theme.Conferences4HallTheme

@Preview
@Composable
private fun MenuItemPreview() {
    Conferences4HallTheme {
        MenuItem(
            menuItem = MenuItemUi.fake
        )
    }
}