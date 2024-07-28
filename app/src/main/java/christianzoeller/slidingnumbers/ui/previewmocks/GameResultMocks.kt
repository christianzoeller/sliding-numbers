package christianzoeller.slidingnumbers.ui.previewmocks

import christianzoeller.slidingnumbers.model.GameResult
import kotlinx.datetime.Clock

object GameResultMocks {
    val first = GameResult(
        score = 1400,
        highest = 128,
        timestamp = Clock.System.now(),
        finalValues = List(16) { 0 }
    )

    val second = GameResult(
        score = 3832,
        highest = 512,
        timestamp = Clock.System.now(),
        finalValues = List(16) { 0 }
    )

    val list = listOf(
        first.apply { id = 0 },
        second.apply { id = 1 }
    )
}