package christianzoeller.slidingnumbers.feature.preferences

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
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.preferences.model.UiMode
import christianzoeller.slidingnumbers.feature.preferences.ui.PreferencesView
import christianzoeller.slidingnumbers.navigation.NavigationDestination
import christianzoeller.slidingnumbers.navigation.NavigationHandler
import christianzoeller.slidingnumbers.navigation.NoOpNavigationHandler
import christianzoeller.slidingnumbers.ui.components.BottomNavigationBar
import christianzoeller.slidingnumbers.ui.components.DefaultErrorView
import christianzoeller.slidingnumbers.ui.components.DefaultLoadingView
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun PreferencesScreen(
    navigationHandler: NavigationHandler,
    viewModel: PreferencesViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    PreferencesScreen(
        navigationHandler = navigationHandler,
        state = state.value,
        onChangeUiMode = viewModel::onChangeUiMode,
        onOssLicensesClick = {
            navigationHandler.navigate(NavigationDestination.OssLicensesOverview)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PreferencesScreen(
    navigationHandler: NavigationHandler,
    state: PreferencesState,
    onChangeUiMode: (Int) -> Unit,
    onOssLicensesClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.preferences_header))
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navigationHandler = navigationHandler)
        }
    ) { contentPadding ->
        when (state) {
            is PreferencesState.Data -> PreferencesView(
                data = state,
                onChangeUiMode = onChangeUiMode,
                onOssLicensesClick = onOssLicensesClick,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(contentPadding)
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 48.dp)
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
        navigationHandler = NoOpNavigationHandler,
        state = PreferencesState.Loading,
        onChangeUiMode = {},
        onOssLicensesClick = {}
    )
}

@CompactPreview
@Composable
private fun PreferencesScreen_Content_Preview() = SlidingNumbersTheme {
    PreferencesScreen(
        navigationHandler = NoOpNavigationHandler,
        state = PreferencesState.Data(
            uiMode = UiMode.Light,
            possibleUiModes = listOf(UiMode.Light, UiMode.Dark, UiMode.System)
        ),
        onChangeUiMode = {},
        onOssLicensesClick = {}
    )
}

@CompactPreview
@Composable
private fun PreferencesScreen_Error_Preview() = SlidingNumbersTheme {
    PreferencesScreen(
        navigationHandler = NoOpNavigationHandler,
        state = PreferencesState.Error,
        onChangeUiMode = {},
        onOssLicensesClick = {}
    )
}