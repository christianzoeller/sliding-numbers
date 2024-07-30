package christianzoeller.slidingnumbers.feature.results.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import christianzoeller.slidingnumbers.repository.GameResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsOverviewViewModel @Inject constructor(
    private val gameResultRepository: GameResultRepository
): ViewModel() {
    private val _state = MutableStateFlow<ResultsOverviewState>(ResultsOverviewState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            gameResultRepository.getAllResults().collect { results ->
                _state.value = when {
                    results.isEmpty() -> ResultsOverviewState.Empty
                    else -> ResultsOverviewState.Data(results)
                }
            }
        }
    }
}