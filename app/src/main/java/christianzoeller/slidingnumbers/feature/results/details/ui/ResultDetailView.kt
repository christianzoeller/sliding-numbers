package christianzoeller.slidingnumbers.feature.results.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import christianzoeller.slidingnumbers.feature.results.details.ResultDetailState
import christianzoeller.slidingnumbers.model.GameResult
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview
import kotlinx.datetime.Clock

@Composable
fun ResultDetailView(
    data: ResultDetailState.Data,
    modifier: Modifier = Modifier
) {
    // TODO implement proper layout
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = data.result.score.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )
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
        modifier = Modifier.padding(16.dp)
    )
}