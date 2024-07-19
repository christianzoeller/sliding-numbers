package christianzoeller.slidingnumbers

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavHostController

sealed class Screen(val route: String) {
    data object Game : BottomNavigationTarget(
        route = "game",
        labelId = R.string.game_tab_label,
        navigationIcon = R.drawable.baseline_gamepad_24,
        navigationIconDescription = R.string.game_tab_icon_description
    ) {
        fun navigate(navController: NavHostController) {
            navController.navigate(route)
        }
    }

    data object Results : BottomNavigationTarget(
        route = "results",
        labelId = R.string.results_tab_label,
        navigationIcon = R.drawable.baseline_emoji_events_24,
        navigationIconDescription = R.string.results_tab_icon_description
    )

    data object ResultDetail : Screen(route = "results/{id}") {
        fun navigate(
            navController: NavHostController,
            gameResultId: Long
        ) {
            navController.navigate("results/$gameResultId")
        }
    }
}

sealed class BottomNavigationTarget(
    route: String,
    @StringRes val labelId: Int,
    @DrawableRes val navigationIcon: Int,
    @StringRes val navigationIconDescription: Int
) : Screen(route)

val bottomBarTargets = listOf(Screen.Game, Screen.Results)