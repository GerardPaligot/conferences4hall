package org.gdglille.devfest.android.theme.m3.schedules.feature

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.gdglille.devfest.android.shared.resources.Resource
import org.gdglille.devfest.android.shared.resources.text_error
import org.gdglille.devfest.android.shared.resources.text_loading
import org.gdglille.devfest.android.theme.m3.schedules.screens.AgendaFiltersScreen
import org.gdglille.devfest.android.theme.m3.style.appbars.AppBarIcons
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AgendaFiltersCompactVM(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    navigationIcon: @Composable (AppBarIcons.() -> Unit)? = null,
    viewModel: AgendaFiltersViewModel = koinViewModel()
) {
    when (val uiState = viewModel.uiState.collectAsState().value) {
        is AgendaFiltersUiState.Loading -> Text(text = stringResource(Resource.string.text_loading))
        is AgendaFiltersUiState.Failure -> Text(text = stringResource(Resource.string.text_error))
        is AgendaFiltersUiState.Success -> {
            AgendaFiltersScreen(
                filtersUi = uiState.filters,
                onFavoriteClick = viewModel::applyFavoriteFilter,
                onCategoryClick = viewModel::applyCategoryFilter,
                onFormatClick = viewModel::applyFormatFilter,
                modifier = modifier,
                containerColor = containerColor,
                navigationIcon = navigationIcon
            )
        }
    }
}
