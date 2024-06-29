package christianzoeller.slidingnumbers.feature.results.overview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.results.overview.ui.ResultsEmptyView
import christianzoeller.slidingnumbers.feature.results.overview.ui.ResultsLoadingView
import christianzoeller.slidingnumbers.feature.results.overview.ui.ResultsView
import christianzoeller.slidingnumbers.model.GameResult
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview
import kotlinx.datetime.Clock

@Composable
fun ResultsOverviewScreen(
    viewModel: ResultsOverviewViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle(
        // TODO workaround for https://issuetracker.google.com/issues/336842920#comment14
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
    )

    ResultsOverviewScreen(
        state = state.value,
        onResultClick = {}, // TODO navigate to details screen
        onStartGameClick = {} // TODO navigate to game screen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultsOverviewScreen(
    state: ResultsOverviewState,
    onResultClick: (Long) -> Unit,
    onStartGameClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.results_overview_header))
                }
            )
        }
    ) { contentPadding ->
        val contentModifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp)

        when (state) {
            is ResultsOverviewState.Data -> ResultsView(
                data = state,
                onResultClick = onResultClick,
                modifier = contentModifier
            )

            ResultsOverviewState.Empty -> ResultsEmptyView(
                onStartGame = onStartGameClick,
                modifier = contentModifier
            )

            ResultsOverviewState.Loading -> ResultsLoadingView(contentModifier)
        }
    }
}

@CompactPreview
@Composable
private fun ResultsOverviewScreen_Loading_Preview() = SlidingNumbersTheme {
    ResultsOverviewScreen(
        state = ResultsOverviewState.Loading,
        onResultClick = {},
        onStartGameClick = {}
    )
}

@CompactPreview
@Composable
private fun ResultsOverviewScreen_Content_Preview() = SlidingNumbersTheme {
    ResultsOverviewScreen(
        state = ResultsOverviewState.Data(
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
        onStartGameClick = {}
    )
}

@CompactPreview
@Composable
private fun ResultsOverviewScreen_Empty_Preview() = SlidingNumbersTheme {
    ResultsOverviewScreen(
        state = ResultsOverviewState.Empty,
        onResultClick = {},
        onStartGameClick = {}
    )
}