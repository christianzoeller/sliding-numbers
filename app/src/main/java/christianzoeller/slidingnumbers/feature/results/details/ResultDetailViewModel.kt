package christianzoeller.slidingnumbers.feature.results.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import christianzoeller.slidingnumbers.navigation.NavigationDestination
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
        .toRoute<NavigationDestination.ResultDetail>()
        .resultId

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            val result = try {
                gameResultRepository.getGameResultById(gameResultId)
            } catch (e: Exception) {
                Log.e("SN", "An error occurred fetching a game result: $e")
                null
            }

            _state.value = when (result) {
                null -> ResultDetailState.Error
                else -> ResultDetailState.Data(result)
            }
        }
    }
}