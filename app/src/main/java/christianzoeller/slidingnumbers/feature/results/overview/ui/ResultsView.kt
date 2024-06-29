package christianzoeller.slidingnumbers.feature.results.overview.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import christianzoeller.slidingnumbers.feature.results.overview.ResultsOverviewState
import christianzoeller.slidingnumbers.model.GameResult
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview
import kotlinx.datetime.Clock

@Composable
fun ResultsView(
    data: ResultsOverviewState.Data,
    onResultClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = data.results,
            key = { it.id }
        ) {
            // TODO implement proper layout
            ListItem(
                headlineContent = { Text(it.score.toString()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onResultClick(it.id) },
                supportingContent = { Text(it.highest.toString()) }
            )
        }
    }
}

@CompactPreview
@Composable
private fun ResultsView_Preview() = SlidingNumbersTheme {
    ResultsView(
        data = ResultsOverviewState.Data(
            results = listOf(
                GameResult(
                    score = 1400,
                    highest = 128,
                    timestamp = Clock.System.now(),
                    finalValues = List(16) { 0 }
                ).apply { id = 0 },
                GameResult(
                    score = 3832,
                    highest = 512,
                    timestamp = Clock.System.now(),
                    finalValues = List(16) { 0 }
                ).apply { id = 1 }
            )
        ),
        onResultClick = {},
        modifier = Modifier.padding(16.dp)
    )
}