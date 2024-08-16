package christianzoeller.slidingnumbers.feature.results.details

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.results.details.ui.ResultDetailView
import christianzoeller.slidingnumbers.ui.components.DefaultErrorView
import christianzoeller.slidingnumbers.ui.components.DefaultLoadingView
import christianzoeller.slidingnumbers.ui.extensions.plus
import christianzoeller.slidingnumbers.ui.extensions.withoutBottomPadding
import christianzoeller.slidingnumbers.ui.previewmocks.GameResultMocks
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun ResultDetailScreen(
    viewModel: ResultDetailViewModel,
    onStartGameClick: () -> Unit,
    onNavigateUp: () -> Unit,
    bottomBarContentPadding: PaddingValues
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    ResultDetailScreen(
        state = state.value,
        onStartGameClick = onStartGameClick,
        onNavigateUp = onNavigateUp,
        bottomBarContentPadding = bottomBarContentPadding
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResultDetailScreen(
    state: ResultDetailState,
    onStartGameClick: () -> Unit,
    onNavigateUp: () -> Unit,
    bottomBarContentPadding: PaddingValues
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.result_detail_header))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.global_navigate_up_icon_description)
                        )
                    }
                }
            )
        }
    ) { innerContentPadding ->
        val contentPadding = innerContentPadding.withoutBottomPadding() + bottomBarContentPadding
        when (state) {
            is ResultDetailState.Data -> ResultDetailView(
                data = state,
                onStartGameClick = onStartGameClick,
                contentPadding = contentPadding
            )

            ResultDetailState.Error -> DefaultErrorView(contentPadding)

            ResultDetailState.Loading -> DefaultLoadingView(contentPadding)
        }
    }
}

@CompactPreview
@Composable
private fun ResultDetailScreen_Loading_Preview() = SlidingNumbersTheme {
    ResultDetailScreen(
        state = ResultDetailState.Loading,
        onStartGameClick = {},
        onNavigateUp = {},
        bottomBarContentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
private fun ResultDetailScreen_Content_Preview() = SlidingNumbersTheme {
    ResultDetailScreen(
        state = ResultDetailState.Data(result = GameResultMocks.first),
        onStartGameClick = {},
        onNavigateUp = {},
        bottomBarContentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
private fun ResultDetailScreen_Error_Preview() = SlidingNumbersTheme {
    ResultDetailScreen(
        state = ResultDetailState.Error,
        onStartGameClick = {},
        onNavigateUp = {},
        bottomBarContentPadding = PaddingValues()
    )
}