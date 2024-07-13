package christianzoeller.slidingnumbers.feature.results.overview.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.results.overview.ResultsOverviewState
import christianzoeller.slidingnumbers.model.GameResult
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ResultsView(
    data: ResultsOverviewState.Data,
    onResultClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(
            items = data.results,
            key = { it.id }
        ) {
            ListItem(
                headlineContent = {
                    Text(stringResource(id = R.string.results_overview_number_of_points, it.score))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onResultClick(it.id) },
                supportingContent = {
                    it.timestamp.formatUserFriendly().let { formattedTimestamp ->
                        Text(text = formattedTimestamp)
                    }
                },
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (it.highest >= 2048) Color.Green else Color.Red,
                                shape = CircleShape
                            )
                            .size(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (it.highest >= 2048)
                                stringResource(id = R.string.results_overview_won_abbreviation)
                            else
                                stringResource(id = R.string.results_overview_lost_abbreviation),
                            color = Color.White
                        )
                    }
                }
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

private fun Instant.formatUserFriendly() =
    this.toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
        .toJavaLocalDateTime()
        .format(
            DateTimeFormatter.ofPattern("MMMM dd yyyy, HH:mm")
        )
