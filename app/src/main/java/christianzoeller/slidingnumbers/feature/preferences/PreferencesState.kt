package christianzoeller.slidingnumbers.feature.preferences

import christianzoeller.slidingnumbers.feature.preferences.model.UiMode

sealed interface PreferencesState {
    data object Loading : PreferencesState
    data class Data(
        val uiMode: UiMode,
        val possibleUiModes: List<UiMode>
    ) : PreferencesState
    data object Error : PreferencesState
}