package christianzoeller.slidingnumbers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class Screen(
    val route: String,
    @StringRes val labelId: Int
) {
    data object Game : BottomNavigationTarget(
        route = "game",
        labelId = R.string.game_tab_label,
        navigationIcon = R.drawable.baseline_gamepad_24,
        navigationIconDescription = R.string.game_tab_icon_description
    )

    data object Results : BottomNavigationTarget(
        route = "results",
        labelId = R.string.results_tab_label,
        navigationIcon = R.drawable.baseline_emoji_events_24,
        navigationIconDescription = R.string.results_tab_icon_description
    )
}

sealed class BottomNavigationTarget(
    route: String,
    @StringRes labelId: Int,
    @DrawableRes val navigationIcon: Int,
    @StringRes val navigationIconDescription: Int
) : Screen(route, labelId)

val bottomBarTargets = listOf(Screen.Game, Screen.Results)