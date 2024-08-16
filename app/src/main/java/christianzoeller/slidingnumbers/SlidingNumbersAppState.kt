package christianzoeller.slidingnumbers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import christianzoeller.slidingnumbers.navigation.Destination
import christianzoeller.slidingnumbers.navigation.TopLevelDestination

@Composable
fun rememberSlidingNumbersAppState(
    navController: NavHostController = rememberNavController()
): SlidingNumbersAppState {
    return remember(
        navController
    ) {
        SlidingNumbersAppState(
            navController = navController
        )
    }
}

@Stable
class SlidingNumbersAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value?.destination

    val topLevelDestinations: List<TopLevelDestination>
        get() = listOf(
            TopLevelDestination.Game,
            TopLevelDestination.Results,
            TopLevelDestination.Preferences
        )

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        navController.navigate(
            route = topLevelDestination,
            navOptions = topLevelNavOptions
        )
    }

    fun navigate(
        destination: Destination,
        builder: NavOptionsBuilder.() -> Unit = {}
    ) {
        val navOptions = navOptions(optionsBuilder = builder)
        navController.navigate(
            route = destination,
            navOptions = navOptions
        )
    }

    fun navigateUp() = navController.navigateUp()
}