package christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensesoverview

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensesoverview.ui.OssLicensesOverviewView
import christianzoeller.slidingnumbers.navigation.NavigationDestination
import christianzoeller.slidingnumbers.navigation.NavigationHandler
import christianzoeller.slidingnumbers.navigation.NoOpNavigationHandler
import christianzoeller.slidingnumbers.ui.components.BottomNavigationBar
import christianzoeller.slidingnumbers.ui.components.DefaultErrorView
import christianzoeller.slidingnumbers.ui.components.DefaultLoadingView
import christianzoeller.slidingnumbers.ui.previewmocks.OssLicenseInfoMocks
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun OssLicensesOverviewScreen(
    navigationHandler: NavigationHandler,
    viewModel: OssLicensesOverviewViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    OssLicensesOverviewScreen(
        navigationHandler = navigationHandler,
        state = state.value,
        onLibraryClick = { libraryName, libraryId, licenses ->
            navigationHandler.navigate(
                NavigationDestination.OssLicenseDetail(
                    libraryName = libraryName,
                    libraryId = libraryId,
                    licenses = licenses
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OssLicensesOverviewScreen(
    navigationHandler: NavigationHandler,
    state: OssLicensesOverviewState,
    onLibraryClick: (libraryName: String, libraryId: String, licenses: List<String>) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.oss_licenses_overview_header))
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navigationHandler = navigationHandler)
        }
    ) { contentPadding ->
        when (state) {
            is OssLicensesOverviewState.Data -> OssLicensesOverviewView(
                data = state,
                onLibraryClick = onLibraryClick,
                contentPadding = contentPadding
            )

            OssLicensesOverviewState.Loading -> DefaultLoadingView(contentPadding)

            OssLicensesOverviewState.Error -> DefaultErrorView(contentPadding)
        }
    }
}

@CompactPreview
@Composable
private fun OssLicensesOverviewScreen_Loading_Preview() = SlidingNumbersTheme {
    OssLicensesOverviewScreen(
        navigationHandler = NoOpNavigationHandler,
        state = OssLicensesOverviewState.Loading,
        onLibraryClick = { _, _, _ -> }
    )
}

@CompactPreview
@Composable
private fun OssLicensesOverviewScreen_Content_Preview() = SlidingNumbersTheme {
    OssLicensesOverviewScreen(
        navigationHandler = NoOpNavigationHandler,
        state = OssLicensesOverviewState.Data(OssLicenseInfoMocks.info),
        onLibraryClick = { _, _, _ -> }
    )
}

@CompactPreview
@Composable
private fun OssLicensesOverviewScreen_Error_Preview() = SlidingNumbersTheme {
    OssLicensesOverviewScreen(
        navigationHandler = NoOpNavigationHandler,
        state = OssLicensesOverviewState.Error,
        onLibraryClick = { _, _, _ -> }
    )
}