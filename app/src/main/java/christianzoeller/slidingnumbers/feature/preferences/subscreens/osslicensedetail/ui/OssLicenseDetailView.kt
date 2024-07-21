package christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensedetail.ui

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensedetail.OssLicenseDetailState
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.License
import kotlinx.collections.immutable.persistentListOf

@Composable
fun OssLicenseDetailView(
    data: OssLicenseDetailState.Data,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = data.library.name,
            style = MaterialTheme.typography.headlineMedium
        )

        data.library.developers
            .mapNotNull { it.name }
            .takeIf { it.isNotEmpty() }
            ?.joinToString()
            ?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it)
            }
    }
}

@CompactPreview
@Composable
private fun OssLicenseDetailView_Preview() = SlidingNumbersTheme {
    OssLicenseDetailView(
        data = OssLicenseDetailState.Data(
            libraryName = "kotlinx.datetime",
            library = Library(
                uniqueId = "1",
                artifactVersion = null,
                name = "kotlinx.datetime",
                description = null,
                website = null,
                developers = persistentListOf(),
                organization = null,
                scm = null
            ),
            licenses = listOf(
                License(
                    name = "Apache-2.0",
                    url = Uri.EMPTY.toString(),
                    hash = ""
                )
            )
        ),
        modifier = Modifier.padding(16.dp)
    )
}