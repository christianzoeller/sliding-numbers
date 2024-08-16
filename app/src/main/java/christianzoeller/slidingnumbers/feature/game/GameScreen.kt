package christianzoeller.slidingnumbers.feature.game

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.slidingnumbers.feature.game.model.SwipeDirection
import christianzoeller.slidingnumbers.feature.game.ui.GameFinishedView
import christianzoeller.slidingnumbers.feature.game.ui.GameNotStartedView
import christianzoeller.slidingnumbers.feature.game.ui.GameRunningView
import christianzoeller.slidingnumbers.ui.extensions.plus
import christianzoeller.slidingnumbers.ui.extensions.withoutBottomPadding
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    bottomBarContentPadding: PaddingValues
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    GameScreen(
        state = state.value,
        onStart = viewModel::onStart,
        onSwipe = viewModel::onSwipe,
        onRestart = viewModel::onRestart,
        bottomBarContentPadding = bottomBarContentPadding
    )
}

@Composable
private fun GameScreen(
    state: GameState,
    onStart: () -> Unit,
    onSwipe: (SwipeDirection) -> Unit,
    onRestart: () -> Unit,
    bottomBarContentPadding: PaddingValues
) {
    Scaffold { innerContentPadding ->
        val contentPadding = innerContentPadding.withoutBottomPadding() + bottomBarContentPadding
        when (state.status) {
            GameStatus.NotStarted -> {
                GameNotStartedView(
                    onStart = onStart,
                    contentPadding = contentPadding
                )
            }

            GameStatus.Running -> {
                GameRunningView(
                    values = state.values,
                    score = state.score,
                    onSwipe = onSwipe,
                    contentPadding = contentPadding
                )
            }

            GameStatus.Finished -> {
                GameFinishedView(
                    values = state.values,
                    score = state.score,
                    won = state.won,
                    onRestart = onRestart,
                    contentPadding = contentPadding
                )
            }
        }
    }
}

@CompactPreview
@Composable
fun GameScreen_NotStarted_Preview() = SlidingNumbersTheme {
    GameScreen(
        state = GameState(status = GameStatus.NotStarted),
        onStart = {},
        onSwipe = {},
        onRestart = {},
        bottomBarContentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
fun GameScreen_Running_Preview() = SlidingNumbersTheme {
    GameScreen(
        state = GameState(status = GameStatus.Running),
        onStart = {},
        onSwipe = {},
        onRestart = {},
        bottomBarContentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
fun GameScreen_Finished_Preview() = SlidingNumbersTheme {
    GameScreen(
        state = GameState(status = GameStatus.Finished),
        onStart = {},
        onSwipe = {},
        onRestart = {},
        bottomBarContentPadding = PaddingValues()
    )
}