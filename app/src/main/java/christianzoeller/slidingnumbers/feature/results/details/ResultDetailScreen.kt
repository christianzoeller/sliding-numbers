package christianzoeller.slidingnumbers.feature.results.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.slidingnumbers.navigation.NavigationDestination
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.results.details.ui.ResultDetailErrorView
import christianzoeller.slidingnumbers.feature.results.details.ui.ResultDetailLoadingView
import christianzoeller.slidingnumbers.feature.results.details.ui.ResultDetailView
import christianzoeller.slidingnumbers.model.GameResult
import christianzoeller.slidingnumbers.navigation.NavigationAction
import christianzoeller.slidingnumbers.navigation.NavigationHandler
import christianzoeller.slidingnumbers.navigation.NoOpNavigationHandler
import christianzoeller.slidingnumbers.ui.components.BottomNavigationBar
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview
import kotlinx.datetime.Clock

@Composable
fun ResultDetailScreen(
    navigationHandler: NavigationHandler,
    viewModel: ResultDetailViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    ResultDetailScreen(
        navigationHandler = navigationHandler,
        state = state.value,
        onStartGameClick = { navigationHandler.navigate(NavigationDestination.Game) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultDetailScreen(
    navigationHandler: NavigationHandler,
    state: ResultDetailState,
    onStartGameClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.result_detail_header))
                },
                navigationIcon = {
                    IconButton(onClick = { navigationHandler.navigate(NavigationAction.Up) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.global_navigate_up_icon_description)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navigationHandler = navigationHandler)
        }
    ) { contentPadding ->
        val contentModifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp)

        when (state) {
            is ResultDetailState.Data -> ResultDetailView(
                data = state,
                onStartGameClick = onStartGameClick,
                modifier = contentModifier
            )

            ResultDetailState.Error -> ResultDetailErrorView(contentModifier)

            ResultDetailState.Loading -> ResultDetailLoadingView(contentModifier)
        }
    }
}

@CompactPreview
@Composable
private fun ResultDetailScreen_Loading_Preview() = SlidingNumbersTheme {
    ResultDetailScreen(
        navigationHandler = NoOpNavigationHandler,
        state = ResultDetailState.Loading,
        onStartGameClick = {}
    )
}

@CompactPreview
@Composable
private fun ResultDetailScreen_Content_Preview() = SlidingNumbersTheme {
    ResultDetailScreen(
        navigationHandler = NoOpNavigationHandler,
        state = ResultDetailState.Data(
            result = GameResult(
                score = 1400,
                highest = 128,
                timestamp = Clock.System.now(),
                finalValues = List(16) { 0 }
            )
        ),
        onStartGameClick = {}
    )
}

@CompactPreview
@Composable
private fun ResultDetailScreen_Error_Preview() = SlidingNumbersTheme {
    ResultDetailScreen(
        navigationHandler = NoOpNavigationHandler,
        state = ResultDetailState.Error,
        onStartGameClick = {}
    )
}