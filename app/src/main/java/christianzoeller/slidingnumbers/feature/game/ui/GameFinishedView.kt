package christianzoeller.slidingnumbers.feature.game.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import christianzoeller.slidingnumbers.ui.components.GameBoard
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun GameFinishedView(
    values: List<Int>,
    score: Int,
    won: Boolean,
    onRestart: () -> Unit,
    contentPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameBoard(
            values = values,
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 48.dp
            )
        )
        Text(
            text = "Score: $score",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = if (won) "You won!" else "Maybe next time...",
            modifier = Modifier.padding(vertical = 24.dp),
            style = if (won)
                MaterialTheme.typography.displaySmall
            else
                MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onRestart,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text(text = "New game")
        }
    }
}

@CompactPreview
@Composable
private fun GameFinishedView_Won_Preview() = SlidingNumbersTheme {
    GameFinishedView(
        values = listOf(
            2, 0, 0, 0,
            2, 4, 2, 8,
            32, 16, 8, 4,
            128, 64, 32, 16
        ),
        score = 512,
        won = true,
        onRestart = {},
        contentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
private fun GameFinishedView_Lost_Preview() = SlidingNumbersTheme {
    GameFinishedView(
        values = listOf(
            2, 0, 0, 0,
            2, 4, 2, 8,
            32, 16, 8, 4,
            128, 64, 32, 16
        ),
        score = 512,
        won = false,
        onRestart = {},
        contentPadding = PaddingValues()
    )
}