package christianzoeller.slidingnumbers.feature.game

import android.util.Log
import androidx.lifecycle.ViewModel
import christianzoeller.slidingnumbers.feature.game.model.SwipeDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    init {
        // TODO temporarily for testing
        _state.value = _state.value.copy(status = GameStatus.Running)
    }

    fun onSwipe(swipeDirection: SwipeDirection) {
        if (_state.value.status != GameStatus.Running) return

        Log.v("SN", "Handling user swipe: $swipeDirection")
    }

    fun switchGameState() {
        _state.value = when (_state.value.status) {
            GameStatus.NotStarted -> _state.value.copy(status = GameStatus.Running)
            GameStatus.Running -> _state.value.copy(status = GameStatus.Finished)
            GameStatus.Finished -> _state.value.copy(status = GameStatus.NotStarted)
        }
    }
}