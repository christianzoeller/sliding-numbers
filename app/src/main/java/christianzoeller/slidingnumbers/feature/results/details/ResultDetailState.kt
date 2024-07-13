package christianzoeller.slidingnumbers.feature.results.details

import christianzoeller.slidingnumbers.model.GameResult

sealed interface ResultDetailState {
    data object Loading : ResultDetailState
    data class Data(val result: GameResult) : ResultDetailState
    data object Error : ResultDetailState
}