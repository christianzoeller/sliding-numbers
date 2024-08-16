package christianzoeller.slidingnumbers.ui.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection

fun PaddingValues.withoutBottomPadding() =
    // Actual layout direction is resolved when applying the padding
    LayoutDirection.Ltr.let {
        PaddingValues(
            start = this.calculateStartPadding(it),
            end = this.calculateEndPadding(it),
            top = calculateTopPadding()
        )
    }

operator fun PaddingValues.plus(other: PaddingValues): PaddingValues {
    // Actual layout direction is resolved when applying the padding
    LayoutDirection.Ltr.let {
        return PaddingValues(
            start = this.calculateStartPadding(it) + other.calculateStartPadding(it),
            end = this.calculateEndPadding(it) + other.calculateEndPadding(it),
            top = this.calculateTopPadding() + other.calculateTopPadding(),
            bottom = this.calculateBottomPadding() + other.calculateBottomPadding()
        )
    }
}
