package christianzoeller.slidingnumbers.feature.results.overview

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
import christianzoeller.slidingnumbers.navigation.NavigationDestination
import christianzoeller.slidingnumbers.navigation.NavigationHandler
import christianzoeller.slidingnumbers.navigation.NoOpNavigationHandler
import christianzoeller.slidingnumbers.ui.components.BottomNavigationBar
import christianzoeller.slidingnumbers.ui.components.DefaultLoadingView
import christianzoeller.slidingnumbers.ui.previewmocks.GameResultMocks
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun ResultsOverviewScreen(
    navigationHandler: NavigationHandler,
    viewModel: ResultsOverviewViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    ResultsOverviewScreen(
        navigationHandler = navigationHandler,
        state = state.value,
        onResultClick = { navigationHandler.navigate(NavigationDestination.ResultDetail(it)) },
        onStartGameClick = { navigationHandler.navigate(NavigationDestination.Game) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultsOverviewScreen(
    navigationHandler: NavigationHandler,
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
        },
        bottomBar = {
            BottomNavigationBar(
                navigationHandler = navigationHandler
            )
        }
    ) { contentPadding ->
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
        navigationHandler = NoOpNavigationHandler,
        state = ResultsOverviewState.Loading,
        onResultClick = {},
        onStartGameClick = {}
    )
}

@CompactPreview
@Composable
private fun ResultsOverviewScreen_Content_Preview() = SlidingNumbersTheme {
    ResultsOverviewScreen(
        navigationHandler = NoOpNavigationHandler,
        state = ResultsOverviewState.Data(results = GameResultMocks.list),
        onResultClick = {},
        onStartGameClick = {}
    )
}

@CompactPreview
@Composable
private fun ResultsOverviewScreen_Empty_Preview() = SlidingNumbersTheme {
    ResultsOverviewScreen(
        navigationHandler = NoOpNavigationHandler,
        state = ResultsOverviewState.Empty,
        onResultClick = {},
        onStartGameClick = {}
    )
}