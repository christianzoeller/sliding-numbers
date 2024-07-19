package christianzoeller.slidingnumbers.feature.results.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import christianzoeller.slidingnumbers.repository.GameResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameResultRepository: GameResultRepository
): ViewModel() {
    private val _state = MutableStateFlow<ResultDetailState>(ResultDetailState.Loading)
    val state = _state.asStateFlow()

    private val gameResultId = savedStateHandle
        .get<String>("id")
        ?.toLongOrNull()

    init {
        load()
    }

    private fun load() {
        if (gameResultId == null) {
            _state.value = ResultDetailState.Error
            return
        }

        viewModelScope.launch {
            val result = gameResultRepository.getGameResultById(gameResultId)
            _state.value = ResultDetailState.Data(result)
        }
    }
}