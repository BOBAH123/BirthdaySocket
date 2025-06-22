package com.vlesko.features.birthday.presentation.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlesko.features.birthday.domain.repository.usecases.CreateSocketConnectionUseCase
import com.vlesko.features.birthday.domain.repository.usecases.ObserveWebSocketMessagesUseCase
import com.vlesko.features.birthday.presentation.models.AppTheme
import com.vlesko.features.birthday.presentation.models.Numbers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BirthdayDetailsViewModel @Inject constructor(
    private val listenSocket: ObserveWebSocketMessagesUseCase,
    private val createConnection: CreateSocketConnectionUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(BirthdayDetailsViewModelState())
    val uiState: StateFlow<BirthdayDetailsViewModelState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            listenSocket.invoke().collectLatest { event ->
                val takeMonth = event.age.years < 1
                updateState { state ->
                    state.copy(
                        appTheme = AppTheme.entries.find { it.key == event.theme }
                            ?: AppTheme.FoxTheme,
                        age = Numbers.getNumberModel(if (takeMonth) event.age.months else event.age.years),
                        isYoungerThanAYear = takeMonth,
                        name = event.name
                    )
                }
            }
        }
    }

    private fun updateState(update: (BirthdayDetailsViewModelState) -> BirthdayDetailsViewModelState) {
        viewModelScope.launch {
            _uiState.emit(
                update(uiState.value)
            )
        }
    }

    fun connectToServer() {
        with(uiState.value) {
            createConnection("$ip:$port")
        }
    }

    fun onIpChanged(value: String) {
        updateState {
            it.copy(
                ip = value
            )
        }
    }

    fun onPortChanged(value: String) {
        updateState {
            it.copy(
                port = value
            )
        }
    }

    fun hideConnectionDialog() {
        updateState {
            it.copy(
                showConnectionDialog = false
            )
        }
    }

    fun showImagePickerDialog(value: Boolean) {
        updateState {
            it.copy(
                showImagePickerDialog = value
            )
        }
    }

    fun onImageSelected(value: Uri) {
        updateState {
            it.copy(
                selectedImageUri = value
            )
        }
    }
}

data class BirthdayDetailsViewModelState(
    val appTheme: AppTheme = AppTheme.FoxTheme,
    val age: Numbers? = null,
    val isYoungerThanAYear: Boolean = true,
    val name: String? = null,
    val ip: String? = null,
    val port: String = "8080",
    val showConnectionDialog: Boolean = true,
    val showImagePickerDialog: Boolean = false,
    val selectedImageUri: Uri? = null
)