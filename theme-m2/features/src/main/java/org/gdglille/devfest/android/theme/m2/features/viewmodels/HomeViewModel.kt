package org.gdglille.devfest.android.theme.m2.features.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.gdglille.devfest.android.ui.m2.BottomActions
import org.gdglille.devfest.android.ui.m2.Screen
import org.gdglille.devfest.android.ui.m2.TopActions
import org.gdglille.devfest.android.ui.resources.actions.BottomAction
import org.gdglille.devfest.android.ui.resources.actions.TabAction
import org.gdglille.devfest.android.ui.resources.models.BottomActionsUi
import org.gdglille.devfest.android.ui.resources.models.ScreenUi
import org.gdglille.devfest.android.ui.resources.models.TabActionsUi
import org.gdglille.devfest.android.ui.resources.models.TopActionsUi
import org.gdglille.devfest.models.ScaffoldConfigUi
import org.gdglille.devfest.models.UserNetworkingUi
import org.gdglille.devfest.repositories.AgendaRepository
import org.gdglille.devfest.repositories.UserRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val screenUi: ScreenUi) : HomeUiState()
    data class Failure(val throwable: Throwable) : HomeUiState()
}

class HomeViewModel(
    private val agendaRepository: AgendaRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _configState = MutableStateFlow(ScaffoldConfigUi())
    private val _routeState = MutableStateFlow<String?>(null)
    private val _uiTopState = MutableStateFlow(TopActionsUi())
    private val _uiTabState = MutableStateFlow(TabActionsUi())
    private val _uiBottomState = MutableStateFlow(BottomActionsUi())
    private val _uiIsFavState = MutableStateFlow(false)
    private val _uiState = combine(
        _routeState,
        _configState,
        _uiIsFavState,
        transform = { route, config, isFav ->
            if (route == null) return@combine HomeUiState.Loading
            updateUiTopActions(route, isFav)
            updateUiTabActions(route, config)
            updateUiBottomActions(config)
            return@combine when (route) {
                Screen.Agenda.route -> HomeUiState.Success(ScreenUi(title = Screen.Agenda.title))
                Screen.Networking.route -> HomeUiState.Success(ScreenUi(title = Screen.Networking.title))
                Screen.Partners.route -> HomeUiState.Success(ScreenUi(title = Screen.Partners.title))
                Screen.Event.route -> HomeUiState.Success(ScreenUi(title = Screen.Event.title))
                else -> TODO()
            }
        }
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = HomeUiState.Loading)
    val uiTopState: StateFlow<TopActionsUi> = _uiTopState
    val uiTabState: StateFlow<TabActionsUi> = _uiTabState
    val uiBottomState: StateFlow<BottomActionsUi> = _uiBottomState
    val uiState: StateFlow<HomeUiState> = _uiState

    private fun updateUiTopActions(route: String, isFav: Boolean) {
        val topActions = when (route) {
            Screen.Agenda.route -> TopActionsUi(
                topActions = arrayListOf(if (isFav) TopActions.favoriteFilled else TopActions.favorite)
            )

            Screen.Networking.route -> TopActionsUi(
                topActions = arrayListOf(TopActions.qrCodeScanner, TopActions.qrCode)
            )

            Screen.Event.route -> TopActionsUi(
                topActions = arrayListOf(TopActions.report)
            )

            else -> TopActionsUi()
        }
        if (topActions != _uiTopState.value) {
            _uiTopState.value = topActions
        }
    }

    private fun updateUiTabActions(route: String, config: ScaffoldConfigUi) {
        val tabActions = when (route) {
            Screen.Agenda.route -> TabActionsUi(
                scrollable = true,
                tabActions = config.agendaTabs.map {
                    val label = DateTimeFormatter
                        .ofPattern("dd MMM")
                        .format(LocalDate.parse(it))
                    TabAction(route = it, 0, label)
                }
            )

            else -> TabActionsUi()
        }
        if (tabActions != _uiTabState.value) {
            _uiTabState.value = tabActions
        }
    }

    private fun updateUiBottomActions(config: ScaffoldConfigUi) {
        val bottomActions = BottomActionsUi(
            arrayListOf<BottomAction>().apply {
                add(BottomActions.agenda)
                if (config.hasNetworking) {
                    add(BottomActions.networking)
                }
                if (config.hasPartnerList) {
                    add(BottomActions.partners)
                }
                add(BottomActions.info)
            }
        )
        if (bottomActions != _uiBottomState.value) {
            _uiBottomState.value = bottomActions
        }
    }

    init {
        viewModelScope.launch {
            arrayListOf(
                async {
                    try {
                        agendaRepository.fetchAndStoreAgenda()
                    } catch (_: Throwable) {
                    }
                },
                async {
                    try {
                        merge(
                            agendaRepository.scaffoldConfig(),
                            agendaRepository.isFavoriteToggled()
                        ).collect {
                            when (it) {
                                is ScaffoldConfigUi -> _configState.value = it
                                is Boolean -> _uiIsFavState.value = it
                                else -> TODO("Flow not implemented")
                            }
                        }
                    } catch (_: Throwable) {
                    }
                }
            ).awaitAll()
        }
    }

    fun screenConfig(route: String) = viewModelScope.launch {
        _routeState.value = route
    }

    fun saveTicket(barcode: String) = viewModelScope.launch {
        agendaRepository.insertOrUpdateTicket(barcode)
    }

    fun saveNetworkingProfile(user: UserNetworkingUi) = viewModelScope.launch {
        userRepository.insertNetworkingProfile(user)
    }

    fun toggleFavoriteFiltering() = viewModelScope.launch {
        agendaRepository.toggleFavoriteFiltering()
    }

    object Factory {
        fun create(agendaRepository: AgendaRepository, userRepository: UserRepository) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    HomeViewModel(
                        agendaRepository = agendaRepository,
                        userRepository = userRepository
                    ) as T
            }
    }
}