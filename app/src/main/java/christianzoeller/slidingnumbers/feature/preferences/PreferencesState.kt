package christianzoeller.slidingnumbers.feature.preferences

sealed interface PreferencesState {
    data object Loading : PreferencesState
    data class Data(val uiMode: Int) : PreferencesState
}