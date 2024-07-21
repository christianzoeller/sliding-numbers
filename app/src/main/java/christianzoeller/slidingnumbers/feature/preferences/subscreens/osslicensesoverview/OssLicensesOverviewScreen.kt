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
import christianzoeller.slidingnumbers.navigation.NavigationHandler
import christianzoeller.slidingnumbers.navigation.NoOpNavigationHandler
import christianzoeller.slidingnumbers.repository.OssLicenseInfo
import christianzoeller.slidingnumbers.ui.components.BottomNavigationBar
import christianzoeller.slidingnumbers.ui.components.DefaultErrorView
import christianzoeller.slidingnumbers.ui.components.DefaultLoadingView
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview
import com.mikepenz.aboutlibraries.entity.Library
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

@Composable
fun OssLicensesOverviewScreen(
    navigationHandler: NavigationHandler,
    viewModel: OssLicensesOverviewViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    OssLicensesOverviewScreen(
        navigationHandler = navigationHandler,
        state = state.value,
        onLibraryClick = { _, _ -> /* TODO */ }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OssLicensesOverviewScreen(
    navigationHandler: NavigationHandler,
    state: OssLicensesOverviewState,
    onLibraryClick: (libraryId: String, licenses: List<String>) -> Unit
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
        onLibraryClick = { _, _ -> }
    )
}

@CompactPreview
@Composable
private fun OssLicensesOverviewScreen_Content_Preview() = SlidingNumbersTheme {
    OssLicensesOverviewScreen(
        navigationHandler = NoOpNavigationHandler,
        state = OssLicensesOverviewState.Data(
            OssLicenseInfo(
                libraries = persistentListOf(
                    Library(
                        uniqueId = "1",
                        artifactVersion = null,
                        name = "kotlinx.datetime",
                        description = null,
                        website = null,
                        developers = persistentListOf(),
                        organization = null,
                        scm = null
                    ),
                    Library(
                        uniqueId = "2",
                        artifactVersion = null,
                        name = "kotlinx.serialization",
                        description = null,
                        website = null,
                        developers = persistentListOf(),
                        organization = null,
                        scm = null
                    )
                ),
                licenses = persistentSetOf()
            )
        ),
        onLibraryClick = { _, _ -> }
    )
}

@CompactPreview
@Composable
private fun OssLicensesOverviewScreen_Error_Preview() = SlidingNumbersTheme {
    OssLicensesOverviewScreen(
        navigationHandler = NoOpNavigationHandler,
        state = OssLicensesOverviewState.Error,
        onLibraryClick = { _, _ -> }
    )
}