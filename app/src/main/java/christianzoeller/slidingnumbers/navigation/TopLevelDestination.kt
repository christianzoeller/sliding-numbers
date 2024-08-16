package christianzoeller.slidingnumbers.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import christianzoeller.slidingnumbers.R
import kotlinx.serialization.Serializable

sealed class TopLevelDestination(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    @StringRes val iconDescription: Int
) {
    @Serializable
    data object Game : TopLevelDestination(
        label = R.string.game_tab_label,
        icon = R.drawable.baseline_gamepad_24,
        iconDescription = R.string.game_tab_icon_description
    )

    @Serializable
    data object Results : TopLevelDestination(
        label = R.string.results_tab_label,
        icon = R.drawable.baseline_emoji_events_24,
        iconDescription = R.string.results_tab_icon_description
    )

    @Serializable
    data object Preferences : TopLevelDestination(
        label = R.string.preferences_tab_label,
        icon = R.drawable.baseline_settings_24,
        iconDescription = R.string.preferences_tab_icon_description
    )

    // Convenience constructor to be used by kotlinx.serialization
    @Suppress("unused")
    private constructor() : this(
        label = 0,
        icon = 0,
        iconDescription = 0
    )
}