package christianzoeller.slidingnumbers.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import christianzoeller.slidingnumbers.R
import kotlinx.serialization.Serializable

sealed interface NavigationDestination {
    @Serializable
    data object Game : BottomNavigationDestination(
        labelId = R.string.game_tab_label,
        navigationIcon = R.drawable.baseline_gamepad_24,
        navigationIconDescription = R.string.game_tab_icon_description
    )

    @Serializable
    data object ResultsOverview : BottomNavigationDestination(
        labelId = R.string.results_tab_label,
        navigationIcon = R.drawable.baseline_emoji_events_24,
        navigationIconDescription = R.string.results_tab_icon_description
    )

    @Serializable
    data class ResultDetail(val resultId: Long) : NavigationDestination

    @Serializable
    data object Preferences : BottomNavigationDestination(
        labelId = R.string.preferences_tab_label,
        navigationIcon = R.drawable.baseline_settings_24,
        navigationIconDescription = R.string.preferences_tab_icon_description
    )

    @Serializable
    data object OssLicensesOverview : NavigationDestination

    @Serializable
    data class OssLicenseDetail(
        val libraryName: String,
        val libraryId: String,
        val licenses: List<String>
    ) : NavigationDestination
}

sealed class BottomNavigationDestination(
    @StringRes val labelId: Int,
    @DrawableRes val navigationIcon: Int,
    @StringRes val navigationIconDescription: Int
): NavigationDestination {
    // Convenience constructor to be used by kotlinx.serialization
    @Suppress("unused")
    private constructor() : this(
        labelId = 0,
        navigationIcon = 0,
        navigationIconDescription = 0
    )
}

val bottomNavigationDestinations = listOf(
    NavigationDestination.Game,
    NavigationDestination.ResultsOverview,
    NavigationDestination.Preferences
)