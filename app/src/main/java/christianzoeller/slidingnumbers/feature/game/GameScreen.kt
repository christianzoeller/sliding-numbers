package christianzoeller.slidingnumbers.feature.game

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.slidingnumbers.feature.game.model.SwipeDirection
import christianzoeller.slidingnumbers.feature.game.ui.GameFinishedView
import christianzoeller.slidingnumbers.feature.game.ui.GameNotStartedView
import christianzoeller.slidingnumbers.feature.game.ui.GameRunningView
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun GameScreen(
    viewModel: GameViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle(
        // TODO workaround for https://issuetracker.google.com/issues/336842920#comment14
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
    )

    GameScreen(
        state = state.value,
        onStart = viewModel::onStart,
        onSwipe = viewModel::onSwipe,
        onRestart = viewModel::onRestart
    )
}

@Composable
fun GameScreen(
    state: GameState,
    onStart: () -> Unit,
    onSwipe: (SwipeDirection) -> Unit,
    onRestart: () -> Unit
) {
    Scaffold { contentPadding ->
        val contentModifier = Modifier
            .padding(contentPadding)
            .fillMaxWidth()

        when (state.status) {
            GameStatus.NotStarted -> {
                GameNotStartedView(
                    onStart = onStart,
                    modifier = contentModifier
                )
            }

            GameStatus.Running -> {
                GameRunningView(
                    values = state.values,
                    score = state.score,
                    onSwipe = onSwipe,
                    modifier = contentModifier
                )
            }

            GameStatus.Finished -> {
                GameFinishedView(
                    values = state.values,
                    score = state.score,
                    won = state.won,
                    onRestart = onRestart,
                    modifier = contentModifier
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
        onRestart = {}
    )
}

@CompactPreview
@Composable
fun GameScreen_Running_Preview() = SlidingNumbersTheme {
    GameScreen(
        state = GameState(status = GameStatus.Running),
        onStart = {},
        onSwipe = {},
        onRestart = {}
    )
}

@CompactPreview
@Composable
fun GameScreen_Finished_Preview() = SlidingNumbersTheme {
    GameScreen(
        state = GameState(status = GameStatus.Finished),
        onStart = {},
        onSwipe = {},
        onRestart = {}
    )
}