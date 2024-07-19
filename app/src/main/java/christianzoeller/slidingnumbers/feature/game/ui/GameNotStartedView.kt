package christianzoeller.slidingnumbers.feature.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
fun GameNotStartedView(
    onStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            GameBoard(
                values = List(16) { 0 },
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 48.dp
                )
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 48.dp
                    )
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "2048",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onStart,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text(text = "Start game")
        }
    }
}

@CompactPreview
@Composable
private fun GameNotStartedView_Preview() = SlidingNumbersTheme {
    GameNotStartedView(
        onStart = {}
    )
}