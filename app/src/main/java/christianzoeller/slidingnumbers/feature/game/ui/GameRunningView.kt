package christianzoeller.slidingnumbers.feature.game.ui

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import christianzoeller.slidingnumbers.feature.game.model.SwipeDirection
import christianzoeller.slidingnumbers.ui.components.GameBoard
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview
import kotlin.math.abs

@Composable
fun GameRunningView(
    values: List<Int>,
    score: Int,
    onSwipe: (SwipeDirection) -> Unit,
    modifier: Modifier = Modifier
) {
    var dragOffset: Offset? by remember { mutableStateOf(null) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameBoard(
            values = values,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {},
                        onDragEnd = {
                            // TODO check whether drag was allowed

                            dragOffset?.let {
                                val xAbs = abs(it.x)
                                val yAbs = abs(it.y)
                                val q = minOf(xAbs, yAbs) / maxOf(xAbs, yAbs)
                                if (q <= 0.5) {
                                    val horizontal = xAbs > yAbs
                                    val direction = when {
                                        horizontal && it.x >= 0 -> SwipeDirection.Right
                                        horizontal && it.x < 0 -> SwipeDirection.Left
                                        !horizontal && it.y < 0 -> SwipeDirection.Up
                                        !horizontal && it.y >= 0 -> SwipeDirection.Down
                                        else -> null
                                    }

                                    direction?.let(onSwipe)
                                } else {
                                    Log.v("SN", "Diagonal swipe detected: (${it.x}, ${it.y})")
                                }

                                dragOffset = null
                            }
                        },
                        onDragCancel = { dragOffset = null },
                        onDrag = { change, dragAmount ->
                            change.consume()

                            dragOffset = if (dragOffset == null) {
                                dragAmount
                            } else {
                                dragOffset?.plus(dragAmount)
                            }
                        }
                    )
                }
                .padding(
                    horizontal = 24.dp,
                    vertical = 48.dp
                )
        )
        Text(
            text = "Score: $score",
            modifier = Modifier.padding(bottom = 32.dp),
            style = MaterialTheme.typography.titleLarge
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
        score = 512,
        onSwipe = {}
    )
}