package org.gdglille.devfest.android.theme.m3.networking.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.gdglille.devfest.models.ui.UserNetworkingUi
import org.gdglille.devfest.repositories.UserRepository

sealed class ContactsUiState {
    data object Loading : ContactsUiState()
    data class Success(val users: ImmutableList<UserNetworkingUi>) : ContactsUiState()
    data class Failure(val throwable: Throwable) : ContactsUiState()
}

class ContactsViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ContactsUiState>(ContactsUiState.Loading)
    val uiState: StateFlow<ContactsUiState> = _uiState

    init {
        viewModelScope.launch {
            try {
                userRepository.fetchNetworking().collect {
                    _uiState.value = ContactsUiState.Success(users = it)
                }
            } catch (error: Throwable) {
                Firebase.crashlytics.recordException(error)
                _uiState.value = ContactsUiState.Failure(throwable = error)
            }
        }
    }

    fun deleteNetworking(email: String) = viewModelScope.launch {
        userRepository.deleteNetworkProfile(email)
    }
}
