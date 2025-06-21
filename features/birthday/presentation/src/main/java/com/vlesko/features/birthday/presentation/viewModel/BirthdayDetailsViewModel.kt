package com.vlesko.features.birthday.presentation.viewModel

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
                _uiState.emit(
                    BirthdayDetailsViewModelState(
                        appTheme = AppTheme.entries.find { it.key == event.theme } ?: AppTheme.FoxTheme,
                        age = Numbers.getNumberModel(if (takeMonth) event.age.months else event.age.years),
                        isYoungerThanAYear = takeMonth,
                        name = event.name
                    )
                )
            }
        }
    }

    fun connectToServer(port: String) {
        createConnection(port)
    }
}

data class BirthdayDetailsViewModelState(
    val appTheme: AppTheme = AppTheme.FoxTheme,
    val age: Numbers? = null,
    val isYoungerThanAYear: Boolean = true,
    val name: String? = null,
)