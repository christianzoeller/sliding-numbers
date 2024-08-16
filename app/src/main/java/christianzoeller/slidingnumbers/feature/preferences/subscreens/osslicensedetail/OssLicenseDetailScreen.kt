package christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensedetail

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensedetail.ui.OssLicenseDetailView
import christianzoeller.slidingnumbers.ui.components.DefaultErrorView
import christianzoeller.slidingnumbers.ui.components.DefaultLoadingView
import christianzoeller.slidingnumbers.ui.extensions.plus
import christianzoeller.slidingnumbers.ui.extensions.withoutBottomPadding
import christianzoeller.slidingnumbers.ui.previewmocks.OssLicenseInfoMocks
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun OssLicenseDetailScreen(
    viewModel: OssLicenseDetailViewModel,
    onNavigateUp: () -> Unit,
    bottomBarContentPadding: PaddingValues
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    OssLicenseDetailScreen(
        state = state.value,
        onNavigateUp = onNavigateUp,
        bottomBarContentPadding = bottomBarContentPadding
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OssLicenseDetailScreen(
    state: OssLicenseDetailState,
    onNavigateUp: () -> Unit,
    bottomBarContentPadding: PaddingValues
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
        state = OssLicenseDetailState.Loading("kotlinx.datetime"),
        onNavigateUp = {},
        bottomBarContentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
private fun OssLicenseDetailScreen_Content_Preview() = SlidingNumbersTheme {
    OssLicenseDetailScreen(
        state = OssLicenseDetailState.Data(
            libraryName = "kotlinx.datetime",
            library = OssLicenseInfoMocks.library,
            licenses = listOf(OssLicenseInfoMocks.license)
        ),
        onNavigateUp = {},
        bottomBarContentPadding = PaddingValues()
    )
}

@CompactPreview
@Composable
private fun OssLicenseDetailScreen_Error_Preview() = SlidingNumbersTheme {
    OssLicenseDetailScreen(
        state = OssLicenseDetailState.Error("kotlinx.datetime"),
        onNavigateUp = {},
        bottomBarContentPadding = PaddingValues()
    )
}