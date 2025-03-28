package christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensesoverview.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensesoverview.OssLicensesOverviewState
import christianzoeller.slidingnumbers.ui.previewmocks.OssLicenseInfoMocks
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun OssLicensesOverviewView(
    data: OssLicensesOverviewState.Data,
    onLibraryClick: (libraryName: String, libraryId: String, licenses: List<String>) -> Unit,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        contentPadding = contentPadding
    ) {
        items(
            items = data.ossLicenseInfo.libraries,
            key = { it.uniqueId }
        ) { library ->
            ListItem(
                headlineContent = { Text(text = library.name) },
                modifier = Modifier.clickable(
                    onClick = {
                        onLibraryClick(
                            library.name,
                            library.uniqueId,
                            library.licenses.map { it.name }
                        )
                    }
                ),
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(
                            id = R.string.oss_licenses_overview_list_item_icon_description,
                            library.name
                        )
                    )
                }
            )
        }
    }
}

@CompactPreview
@Composable
private fun OssLicensesOverviewView_Preview() = SlidingNumbersTheme {
    OssLicensesOverviewView(
        data = OssLicensesOverviewState.Data(OssLicenseInfoMocks.info),
        onLibraryClick = { _, _, _ -> },
        contentPadding = PaddingValues()
    )
}

