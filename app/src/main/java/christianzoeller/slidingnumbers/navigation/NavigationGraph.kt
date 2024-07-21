package christianzoeller.slidingnumbers.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import christianzoeller.slidingnumbers.feature.game.GameScreen
import christianzoeller.slidingnumbers.feature.game.GameViewModel
import christianzoeller.slidingnumbers.feature.preferences.PreferencesScreen
import christianzoeller.slidingnumbers.feature.preferences.PreferencesViewModel
import christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensedetail.OssLicenseDetailScreen
import christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensedetail.OssLicenseDetailViewModel
import christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensesoverview.OssLicensesOverviewScreen
import christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensesoverview.OssLicensesOverviewViewModel
import christianzoeller.slidingnumbers.feature.results.details.ResultDetailScreen
import christianzoeller.slidingnumbers.feature.results.details.ResultDetailViewModel
import christianzoeller.slidingnumbers.feature.results.overview.ResultsOverviewScreen
import christianzoeller.slidingnumbers.feature.results.overview.ResultsOverviewViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val navigationHandler = rememberNavigationHandler(navController)

    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Game
    ) {
        composable<NavigationDestination.Game> {
            navigationHandler.selectedTab.value = NavigationDestination.Game

            val viewModel = hiltViewModel<GameViewModel>()
            GameScreen(navigationHandler, viewModel)
        }

        composable<NavigationDestination.ResultsOverview> {
            navigationHandler.selectedTab.value = NavigationDestination.ResultsOverview

            val viewModel = hiltViewModel<ResultsOverviewViewModel>()
            ResultsOverviewScreen(navigationHandler, viewModel)
        }

        composable<NavigationDestination.ResultDetail> {
            navigationHandler.selectedTab.value = NavigationDestination.ResultsOverview

            val viewModel = hiltViewModel<ResultDetailViewModel>()
            ResultDetailScreen(navigationHandler, viewModel)
        }

        composable<NavigationDestination.Preferences> {
            navigationHandler.selectedTab.value = NavigationDestination.Preferences

            val viewModel = hiltViewModel<PreferencesViewModel>()
            PreferencesScreen(navigationHandler, viewModel)
        }

        composable<NavigationDestination.OssLicensesOverview> {
            navigationHandler.selectedTab.value = NavigationDestination.Preferences

            val viewModel = hiltViewModel<OssLicensesOverviewViewModel>()
            OssLicensesOverviewScreen(navigationHandler, viewModel)
        }

        composable<NavigationDestination.OssLicenseDetail> {
            navigationHandler.selectedTab.value = NavigationDestination.Preferences

            val viewModel = hiltViewModel<OssLicenseDetailViewModel>()
            OssLicenseDetailScreen(navigationHandler, viewModel)
        }
    }
}

@Composable
private fun rememberNavigationHandler(navController: NavHostController) =
    remember(navController) {
        Navigator(navController)
    }

private class Navigator(
    private val navController: NavHostController
) : NavigationHandler {
    override val selectedTab = MutableStateFlow<BottomNavigationDestination>(NavigationDestination.Game)

    override fun navigate(action: NavigationAction) {
        when (action) {
            is NavigationAction.Navigate -> when (action.destination) {
                is BottomNavigationDestination -> bottomBarNavigation(
                    navController = navController,
                    destination = action.destination
                )
                else -> navController.navigate(action.destination)
            }
            NavigationAction.Up -> navController.navigateUp()
        }
    }

    override fun navigate(destination: NavigationDestination) {
        navigate(NavigationAction.Navigate(destination))
    }
}

private fun bottomBarNavigation(
    navController: NavHostController,
    destination: BottomNavigationDestination
) {
    navController.navigate(destination) {
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
}