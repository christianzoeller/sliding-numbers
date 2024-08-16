package christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensesoverview

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
import christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensesoverview.ui.OssLicensesOverviewView
import christianzoeller.slidingnumbers.ui.components.DefaultErrorView
import christianzoeller.slidingnumbers.ui.components.DefaultLoadingView
import christianzoeller.slidingnumbers.ui.extensions.plus
import christianzoeller.slidingnumbers.ui.extensions.withoutBottomPadding
import christianzoeller.slidingnumbers.ui.previewmocks.OssLicenseInfoMocks
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun OssLicensesOverviewScreen(
    viewModel: OssLicensesOverviewViewModel,
    onLibraryClick: (libraryName: String, libraryId: String, licenses: List<String>) -> Unit,
    onNavigateUp: () -> Unit,
    bottomBarContentPadding: PaddingValues
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    OssLicensesOverviewScreen(
        state = state.value,
        onLibraryClick = onLibraryClick,
        onNavigateUp = onNavigateUp,
        bottomBarContentPadding = bottomBarContentPadding
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OssLicensesOverviewScreen(
    state: OssLicensesOverviewState,
    onLibraryClick: (libraryName: String, libraryId: String, licenses: List<String>) -> Unit,
    onNavigateUp: () -> Unit,
    bottomBarContentPadding: PaddingValues
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.oss_licenses_overview_header))
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
        state = OssLicensesOverviewState.Loading,
        onLibraryClick = { _, _, _ -> },
        onNavigateUp = {},
        bottomBarContentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
private fun OssLicensesOverviewScreen_Content_Preview() = SlidingNumbersTheme {
    OssLicensesOverviewScreen(
        state = OssLicensesOverviewState.Data(OssLicenseInfoMocks.info),
        onLibraryClick = { _, _, _ -> },
        onNavigateUp = {},
        bottomBarContentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
private fun OssLicensesOverviewScreen_Error_Preview() = SlidingNumbersTheme {
    OssLicensesOverviewScreen(
        state = OssLicensesOverviewState.Error,
        onLibraryClick = { _, _, _ -> },
        onNavigateUp = {},
        bottomBarContentPadding = PaddingValues()
    )
}