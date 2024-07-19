package christianzoeller.slidingnumbers.feature.results.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.results.details.ResultDetailState
import christianzoeller.slidingnumbers.model.GameResult
import christianzoeller.slidingnumbers.ui.components.GameBoard
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview
import kotlinx.datetime.Clock

@Composable
fun ResultDetailView(
    data: ResultDetailState.Data,
    onStartGameClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(
                id = R.string.result_detail_your_score,
                data.result.score
            ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )
        GameBoard(
            values = data.result.finalValues,
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 48.dp
            )
        )
        Text(
            text = if (data.result.highest >= 2048) {
                stringResource(id = R.string.result_detail_you_won)
            } else {
                stringResource(id = R.string.result_detail_you_lost)
            },
            modifier = Modifier.padding(
                start = 48.dp, end = 48.dp,
                bottom = 24.dp
            ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
        Button(onClick = onStartGameClick) {
            Text(stringResource(id = R.string.result_detail_play_again))
        }
    }
}

@CompactPreview
@Composable
private fun ResultDetailView_Preview() = SlidingNumbersTheme {
    ResultDetailView(
        data = ResultDetailState.Data(
            result = GameResult(
                score = 1400,
                highest = 128,
                timestamp = Clock.System.now(),
                finalValues = List(16) { 0 }
            )
        ),
        onStartGameClick = {},
        modifier = Modifier.padding(16.dp)
    )
}