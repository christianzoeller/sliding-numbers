package christianzoeller.slidingnumbers.feature.results.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.Screen
import christianzoeller.slidingnumbers.feature.results.details.ui.ResultDetailErrorView
import christianzoeller.slidingnumbers.feature.results.details.ui.ResultDetailLoadingView
import christianzoeller.slidingnumbers.feature.results.details.ui.ResultDetailView
import christianzoeller.slidingnumbers.model.GameResult
import christianzoeller.slidingnumbers.ui.components.BottomNavigationBar
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview
import kotlinx.datetime.Clock

@Composable
fun ResultDetailScreen(
    navController: NavHostController,
    viewModel: ResultDetailViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle(
        // TODO workaround for https://issuetracker.google.com/issues/336842920#comment14
        lifecycleOwner = androidx.compose.ui.platform.LocalLifecycleOwner.current
    )

    ResultDetailScreen(
        navController = navController,
        state = state.value,
        onStartGameClick = { Screen.Game.navigate(navController) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultDetailScreen(
    navController: NavHostController,
    state: ResultDetailState,
    onStartGameClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.result_detail_header))
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
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
        navController = rememberNavController(),
        state = ResultDetailState.Loading,
        onStartGameClick = {}
    )
}

@CompactPreview
@Composable
private fun ResultDetailScreen_Content_Preview() = SlidingNumbersTheme {
    ResultDetailScreen(
        navController = rememberNavController(),
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
        navController = rememberNavController(),
        state = ResultDetailState.Error,
        onStartGameClick = {}
    )
}