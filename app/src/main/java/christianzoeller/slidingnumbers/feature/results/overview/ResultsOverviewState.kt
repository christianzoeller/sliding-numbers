package christianzoeller.slidingnumbers.feature.results.overview

import christianzoeller.slidingnumbers.model.GameResult

sealed interface ResultsOverviewState {
    data object Loading : ResultsOverviewState
    data class Data(
        val results: List<GameResult>
    ) : ResultsOverviewState
    data object Empty : ResultsOverviewState
}