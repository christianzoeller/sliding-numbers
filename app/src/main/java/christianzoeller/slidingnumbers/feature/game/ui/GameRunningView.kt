package christianzoeller.slidingnumbers.feature.game.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun GameRunningView(
    values: List<Int>,
    onValuesChange: (List<Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        GameBoard(
            values = values,
            onValuesChange = onValuesChange,
            modifier = Modifier
        )
    }
}

@CompactPreview
@Composable
private fun GameRunningView_Preview() = SlidingNumbersTheme {
    GameRunningView(
        values = listOf(
            2, 0, 0, 0,
            2, 4, 2, 8,
            32, 16, 8, 4,
            128, 64, 32, 16
        ),
        onValuesChange = {}
    )
}