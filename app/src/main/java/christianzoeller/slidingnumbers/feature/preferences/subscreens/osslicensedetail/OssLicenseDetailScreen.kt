package christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensedetail

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensedetail.ui.OssLicenseDetailView
import christianzoeller.slidingnumbers.navigation.NavigationAction
import christianzoeller.slidingnumbers.navigation.NavigationHandler
import christianzoeller.slidingnumbers.navigation.NoOpNavigationHandler
import christianzoeller.slidingnumbers.ui.components.BottomNavigationBar
import christianzoeller.slidingnumbers.ui.components.DefaultErrorView
import christianzoeller.slidingnumbers.ui.components.DefaultLoadingView
import christianzoeller.slidingnumbers.ui.previewmocks.OssLicenseInfoMocks
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun OssLicenseDetailScreen(
    navigationHandler: NavigationHandler,
    viewModel: OssLicenseDetailViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    OssLicenseDetailScreen(
        navigationHandler = navigationHandler,
        state = state.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OssLicenseDetailScreen(
    navigationHandler: NavigationHandler,
    state: OssLicenseDetailState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.oss_license_detail_header,
                            state.libraryName
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
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
        when (state) {
            is OssLicenseDetailState.Data -> OssLicenseDetailView(
                data = state,
                contentPadding = contentPadding
            )

            is OssLicenseDetailState.Loading -> DefaultLoadingView(contentPadding)

            is OssLicenseDetailState.Error -> DefaultErrorView(contentPadding)
        }
    }
}

@CompactPreview
@Composable
private fun OssLicenseDetailScreen_Loading_Preview() = SlidingNumbersTheme {
    OssLicenseDetailScreen(
        navigationHandler = NoOpNavigationHandler,
        state = OssLicenseDetailState.Loading("kotlinx.datetime")
    )
}

@CompactPreview
@Composable
private fun OssLicenseDetailScreen_Content_Preview() = SlidingNumbersTheme {
    OssLicenseDetailScreen(
        navigationHandler = NoOpNavigationHandler,
        state = OssLicenseDetailState.Data(
            libraryName = "kotlinx.datetime",
            library = OssLicenseInfoMocks.library,
            licenses = listOf(OssLicenseInfoMocks.license)
        )
    )
}

@CompactPreview
@Composable
private fun OssLicenseDetailScreen_Error_Preview() = SlidingNumbersTheme {
    OssLicenseDetailScreen(
        navigationHandler = NoOpNavigationHandler,
        state = OssLicenseDetailState.Error("kotlinx.datetime")
    )
}