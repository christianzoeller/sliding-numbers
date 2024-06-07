package christianzoeller.slidingnumbers.feature.game.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun GameBoard(
    values: List<Int>,
    onValuesChange: (List<Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    assert(values.size == 16)

    val textMeasurer = rememberTextMeasurer()

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val moreWidthThanHeight = maxWidth >= maxHeight
        val canvasBaseModifier = if (moreWidthThanHeight)
            Modifier.fillMaxHeight()
        else
            Modifier.fillMaxWidth()

        val localDensity = LocalDensity.current
        val dimensions = remember(this.constraints, localDensity) {
            with (localDensity) {
                val tileMargin = 8 // DP // TODO make dependent on available space
                val canvasLength = if (moreWidthThanHeight) maxHeight else maxWidth
                val allMargins = (5 * tileMargin).dp
                val tileLength = (canvasLength - allMargins) / 4

                BoardDimensions(
                    length = canvasLength.toPx(),
                    tileLength = tileLength.toPx(),
                    tileMargin = tileMargin.dp.toPx(),
                    tileCornerRadius = 4.dp.toPx()
                )
            }
        }

        val tileColor = MaterialTheme.colorScheme.primaryContainer
        val tileContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        val tileTextStyle = MaterialTheme.typography.bodyLarge

        Canvas(modifier = canvasBaseModifier.aspectRatio(1f)) {
            for (i in 0..values.lastIndex) {
                val m = i / 4
                val n = i % 4

                val x = n * dimensions.tileLength + (n + 1) * dimensions.tileMargin
                val y = m * dimensions.tileLength + (m + 1) * dimensions.tileMargin

                drawRoundRect(
                    color = tileColor,
                    topLeft = Offset(x, y),
                    size = Size(dimensions.tileLength, dimensions.tileLength),
                    cornerRadius = CornerRadius(
                        x = dimensions.tileCornerRadius,
                        y = dimensions.tileCornerRadius
                    )
                )

                val value = values[i]
                val textLayoutResult = textMeasurer.measure(
                    text = value.toString(),
                    style = tileTextStyle
                )
                val tileCenter = Offset(
                    x = x + dimensions.tileLength / 2,
                    y = y + dimensions.tileLength / 2
                )
                val textTopLeft = Offset(
                    x = tileCenter.x - textLayoutResult.size.width / 2,
                    y = tileCenter.y - textLayoutResult.size.height / 2
                )
                textLayoutResult.size

                drawText(
                    textLayoutResult = textLayoutResult,
                    color = tileContentColor,
                    topLeft = textTopLeft
                )
            }
        }
    }
}

private data class BoardDimensions(
    val length: Float,
    val tileLength: Float,
    val tileMargin: Float,
    val tileCornerRadius: Float
)

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
        onValuesChange = {},
        modifier = Modifier.padding(32.dp)
    )
}