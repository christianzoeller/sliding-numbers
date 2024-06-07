package christianzoeller.slidingnumbers.feature.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        onSwitchState = viewModel::switchGameState
    )
}

@Composable
fun GameScreen(
    state: GameState,
    onSwitchState: () -> Unit
) {
    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            when (state.status) {
                GameStatus.NotStarted -> {
                    Text(
                        text = "Not Started",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }

                GameStatus.Running -> {
                    GameRunningView(
                        values = state.values,
                        onValuesChange = {}
                    )
                }

                GameStatus.Finished -> {
                    Text(
                        text = "Finished",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }

            Button(onClick = onSwitchState) {
                Text(text = "Switch status")
            }
        }
    }
}

@CompactPreview
@Composable
fun GameScreen_NotStarted_Preview() = SlidingNumbersTheme {
    GameScreen(
        state = GameState(status = GameStatus.NotStarted),
        onSwitchState = {}
    )
}

@CompactPreview
@Composable
fun GameScreen_Running_Preview() = SlidingNumbersTheme {
    GameScreen(
        state = GameState(status = GameStatus.Running),
        onSwitchState = {}
    )
}

@CompactPreview
@Composable
fun GameScreen_Finished_Preview() = SlidingNumbersTheme {
    GameScreen(
        state = GameState(status = GameStatus.Finished),
        onSwitchState = {}
    )
}