package christianzoeller.slidingnumbers.feature.preferences.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.preferences.PreferencesState
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun PreferencesView(
    data: PreferencesState.Data,
    onChangeUiMode: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        when (data.uiMode) {
            Configuration.UI_MODE_NIGHT_YES -> Text(
                text = stringResource(id = R.string.preferences_ui_mode_light)
            )

            Configuration.UI_MODE_NIGHT_NO -> Text(
                text = stringResource(id = R.string.preferences_ui_mode_dark)
            )

            Configuration.UI_MODE_NIGHT_UNDEFINED -> Text(
                text = stringResource(id = R.string.preferences_ui_mode_system)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val nextMode = when (data.uiMode) {
                    Configuration.UI_MODE_NIGHT_YES -> Configuration.UI_MODE_NIGHT_NO
                    Configuration.UI_MODE_NIGHT_NO -> Configuration.UI_MODE_NIGHT_UNDEFINED
                    Configuration.UI_MODE_NIGHT_UNDEFINED -> Configuration.UI_MODE_NIGHT_YES
                    else -> 0
                }
                onChangeUiMode(nextMode)
            }
        ) {
            Text(text = "Change")
        }
    }
}

@CompactPreview
@Composable
private fun PreferencesView_Preview() = SlidingNumbersTheme {
    PreferencesView(
        data = PreferencesState.Data(
            uiMode = Configuration.UI_MODE_NIGHT_YES
        ),
        onChangeUiMode = {},
        modifier = Modifier.padding(16.dp)
    )
}