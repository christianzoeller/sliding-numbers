package christianzoeller.slidingnumbers.feature.game

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    fun switchGameState() {
        _state.value = when (_state.value.status) {
            GameStatus.NotStarted -> _state.value.copy(status = GameStatus.Running)
            GameStatus.Running -> _state.value.copy(status = GameStatus.Finished)
            GameStatus.Finished -> _state.value.copy(status = GameStatus.NotStarted)
        }
    }
}