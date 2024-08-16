package christianzoeller.slidingnumbers.feature.preferences

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.preferences.model.UiMode
import christianzoeller.slidingnumbers.feature.preferences.ui.PreferencesView
import christianzoeller.slidingnumbers.ui.components.DefaultErrorView
import christianzoeller.slidingnumbers.ui.components.DefaultLoadingView
import christianzoeller.slidingnumbers.ui.extensions.plus
import christianzoeller.slidingnumbers.ui.extensions.withoutBottomPadding
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun PreferencesScreen(
    viewModel: PreferencesViewModel,
    onOssLicensesClick: () -> Unit,
    bottomBarContentPadding: PaddingValues
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    PreferencesScreen(
        state = state.value,
        onChangeUiMode = viewModel::onChangeUiMode,
        onOssLicensesClick = onOssLicensesClick,
        bottomBarContentPadding = bottomBarContentPadding
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PreferencesScreen(
    state: PreferencesState,
    onChangeUiMode: (Int) -> Unit,
    onOssLicensesClick: () -> Unit,
    bottomBarContentPadding: PaddingValues
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.preferences_header))
                }
            )
        }
    ) { innerContentPadding ->
        val contentPadding = innerContentPadding.withoutBottomPadding() + bottomBarContentPadding
        when (state) {
            is PreferencesState.Data -> PreferencesView(
                data = state,
                onChangeUiMode = onChangeUiMode,
                onOssLicensesClick = onOssLicensesClick,
                contentPadding = contentPadding
            )

            PreferencesState.Loading -> DefaultLoadingView(contentPadding)

            PreferencesState.Error -> DefaultErrorView(contentPadding)
        }
    }
}

@CompactPreview
@Composable
private fun PreferencesScreen_Loading_Preview() = SlidingNumbersTheme {
    PreferencesScreen(
        state = PreferencesState.Loading,
        onChangeUiMode = {},
        onOssLicensesClick = {},
        bottomBarContentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
private fun PreferencesScreen_Content_Preview() = SlidingNumbersTheme {
    PreferencesScreen(
        state = PreferencesState.Data(
            uiMode = UiMode.Light,
            possibleUiModes = listOf(UiMode.Light, UiMode.Dark, UiMode.System)
        ),
        onChangeUiMode = {},
        onOssLicensesClick = {},
        bottomBarContentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
private fun PreferencesScreen_Error_Preview() = SlidingNumbersTheme {
    PreferencesScreen(
        state = PreferencesState.Error,
        onChangeUiMode = {},
        onOssLicensesClick = {},
        bottomBarContentPadding = PaddingValues()
    )
}