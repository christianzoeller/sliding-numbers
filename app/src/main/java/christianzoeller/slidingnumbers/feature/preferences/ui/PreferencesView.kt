package christianzoeller.slidingnumbers.feature.preferences.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import christianzoeller.slidingnumbers.R
import christianzoeller.slidingnumbers.feature.preferences.PreferencesState
import christianzoeller.slidingnumbers.feature.preferences.model.UiMode
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun PreferencesView(
    data: PreferencesState.Data,
    onChangeUiMode: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AppearanceSection(data = data, onChangeUiMode = onChangeUiMode)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppearanceSection(
    data: PreferencesState.Data,
    onChangeUiMode: (Int) -> Unit,
) {
    Text(
        text = stringResource(id = R.string.preferences_ui_mode_label),
        style = MaterialTheme.typography.bodyLarge
    )
    Spacer(modifier = Modifier.height(16.dp))
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        data.possibleUiModes.forEachIndexed { index, uiMode ->
            SegmentedButton(
                selected = uiMode.value == data.uiMode.value,
                onClick = { onChangeUiMode(uiMode.value) },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = data.possibleUiModes.size
                )
            ) {
                Text(text = stringResource(id = uiMode.label))
            }
        }
    }
}

@CompactPreview
@Composable
private fun PreferencesView_Preview() = SlidingNumbersTheme {
    PreferencesView(
        data = PreferencesState.Data(
            uiMode = UiMode.Light,
            possibleUiModes = listOf(UiMode.Light, UiMode.Dark, UiMode.System)
        ),
        onChangeUiMode = {},
        modifier = Modifier.padding(16.dp)
    )
}