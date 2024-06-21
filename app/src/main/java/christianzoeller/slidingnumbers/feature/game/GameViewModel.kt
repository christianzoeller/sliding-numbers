package christianzoeller.slidingnumbers.feature.game

import androidx.lifecycle.ViewModel
import christianzoeller.slidingnumbers.feature.game.model.SwipeDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    fun onStart() {
        if (_state.value.status != GameStatus.NotStarted) return

        _state.update {
            val freshGame = it.copy(status = GameStatus.Running)
            freshGame.addTile()?.addTile() ?: it
        }
    }

    fun onSwipe(swipeDirection: SwipeDirection) {
        if (_state.value.status != GameStatus.Running) return
        val current = _state.value

        val swiped = when (swipeDirection) {
            SwipeDirection.Right -> current.swipeRight()
            SwipeDirection.Left -> current.swipeLeft()
            SwipeDirection.Up -> current.swipeUp()
            SwipeDirection.Down -> current.swipeDown()
        }

        val updatedStatus = swiped?.updateStatus()
        val finalState = if (updatedStatus?.status != GameStatus.Finished) {
            updatedStatus?.addTile()
        } else updatedStatus

        _state.value = finalState ?: current
    }

    fun onRestart() {
        if (_state.value.status != GameStatus.Finished) return

        _state.update {
            val freshGame = GameState().copy(status = GameStatus.Running)
            freshGame.addTile()?.addTile() ?: it
        }
    }
}