package com.vlesko.features.birthday.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.vlesko.features.birthday.presentation.AppTheme
import com.vlesko.features.birthday.presentation.Numbers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BirthdayDetailsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(BirthdayDetailsViewModelState())
    val uiState: StateFlow<BirthdayDetailsViewModelState> = _uiState
}

data class BirthdayDetailsViewModelState(
    val appTheme: AppTheme = AppTheme.FoxTheme,
    val age: Numbers? = null,
    val isYoungerThanAYear: Boolean = true,
    val name: String? = null,
)