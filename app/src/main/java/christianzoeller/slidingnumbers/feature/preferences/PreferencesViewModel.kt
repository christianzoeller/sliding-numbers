package christianzoeller.slidingnumbers.feature.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import christianzoeller.slidingnumbers.feature.preferences.model.UiMode
import christianzoeller.slidingnumbers.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {
    private val _state = MutableStateFlow<PreferencesState>(PreferencesState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.uiModeFlow.collect { uiMode ->
                _state.value = UiMode.getByValue(uiMode)?.let {
                    PreferencesState.Data(it, UiMode.all)
                } ?: PreferencesState.Error
            }
        }
    }

    fun onChangeUiMode(newMode: Int) {
        viewModelScope.launch {
            preferencesRepository.changeUiModePreference(newMode)
        }
    }
}