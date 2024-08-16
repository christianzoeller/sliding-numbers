package christianzoeller.slidingnumbers.feature.results.overview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.results.overview.ui.ResultsOverviewEmptyView
import christianzoeller.slidingnumbers.feature.results.overview.ui.ResultsOverviewView
import christianzoeller.slidingnumbers.ui.components.DefaultLoadingView
import christianzoeller.slidingnumbers.ui.extensions.plus
import christianzoeller.slidingnumbers.ui.extensions.withoutBottomPadding
import christianzoeller.slidingnumbers.ui.previewmocks.GameResultMocks
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun ResultsOverviewScreen(
    viewModel: ResultsOverviewViewModel,
    onResultClick: (Long) -> Unit,
    onStartGameClick: () -> Unit,
    bottomBarContentPadding: PaddingValues
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    ResultsOverviewScreen(
        state = state.value,
        onResultClick = onResultClick,
        onStartGameClick = onStartGameClick,
        bottomBarContentPadding = bottomBarContentPadding
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultsOverviewScreen(
    state: ResultsOverviewState,
    onResultClick: (Long) -> Unit,
    onStartGameClick: () -> Unit,
    bottomBarContentPadding: PaddingValues
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.results_overview_header))
                }
            )
        }
    ) { innerContentPadding ->
        val contentPadding = innerContentPadding.withoutBottomPadding() + bottomBarContentPadding
        when (state) {
            is ResultsOverviewState.Data -> ResultsOverviewView(
                data = state,
                onResultClick = onResultClick,
                contentPadding = contentPadding
            )

            ResultsOverviewState.Empty -> ResultsOverviewEmptyView(
                onStartGame = onStartGameClick,
                contentPadding = contentPadding
            )

            ResultsOverviewState.Loading -> DefaultLoadingView(contentPadding)
        }
    }
}

@CompactPreview
@Composable
private fun ResultsOverviewScreen_Loading_Preview() = SlidingNumbersTheme {
    ResultsOverviewScreen(
        state = ResultsOverviewState.Loading,
        onResultClick = {},
        onStartGameClick = {},
        bottomBarContentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
private fun ResultsOverviewScreen_Content_Preview() = SlidingNumbersTheme {
    ResultsOverviewScreen(
        state = ResultsOverviewState.Data(results = GameResultMocks.list),
        onResultClick = {},
        onStartGameClick = {},
        bottomBarContentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
private fun ResultsOverviewScreen_Empty_Preview() = SlidingNumbersTheme {
    ResultsOverviewScreen(
        state = ResultsOverviewState.Empty,
        onResultClick = {},
        onStartGameClick = {},
        bottomBarContentPadding = PaddingValues()
    )
}