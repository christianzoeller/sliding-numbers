package christianzoeller.slidingnumbers.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import christianzoeller.slidingnumbers.SlidingNumbersAppState
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

@Composable
fun SlidingNumbersNavHost(
    appState: SlidingNumbersAppState,
    bottomBarContentPadding: PaddingValues
) {
    NavHost(
        navController = appState.navController,
        startDestination = TopLevelDestination.Game
    ) {
        navigation<TopLevelDestination.Game>(
            startDestination = Destination.Game
        ) {
            composable<Destination.Game> {
                val viewModel = hiltViewModel<GameViewModel>()
                GameScreen(viewModel, bottomBarContentPadding)
            }
        }

        navigation<TopLevelDestination.Results>(
            startDestination = Destination.ResultsOverview
        ) {
            composable<Destination.ResultsOverview> {
                val viewModel = hiltViewModel<ResultsOverviewViewModel>()
                ResultsOverviewScreen(
                    viewModel = viewModel,
                    onResultClick = { appState.navigate(Destination.ResultDetail(it)) },
                    onStartGameClick = { appState.navigateToTopLevelDestination(TopLevelDestination.Game) },
                    bottomBarContentPadding = bottomBarContentPadding
                )
            }

            composable<Destination.ResultDetail> {
                val viewModel = hiltViewModel<ResultDetailViewModel>()
                ResultDetailScreen(
                    viewModel = viewModel,
                    onStartGameClick = { appState.navigateToTopLevelDestination(TopLevelDestination.Game) },
                    onNavigateUp = { appState.navigateUp() },
                    bottomBarContentPadding = bottomBarContentPadding
                )
            }
        }

        navigation<TopLevelDestination.Preferences>(
            startDestination = Destination.Preferences
        ) {
            composable<Destination.Preferences> {
                val viewModel = hiltViewModel<PreferencesViewModel>()
                PreferencesScreen(
                    viewModel = viewModel,
                    onOssLicensesClick = { appState.navigate(Destination.OssLicensesOverview) },
                    bottomBarContentPadding = bottomBarContentPadding
                )
            }

            composable<Destination.OssLicensesOverview> {
                val viewModel = hiltViewModel<OssLicensesOverviewViewModel>()
                OssLicensesOverviewScreen(
                    viewModel = viewModel,
                    onLibraryClick = { libraryName, libraryId, licenses ->
                        appState.navigate(
                            Destination.OssLicenseDetail(
                                libraryName = libraryName,
                                libraryId = libraryId,
                                licenses = licenses
                            )
                        )
                    },
                    onNavigateUp = { appState.navigateUp() },
                    bottomBarContentPadding = bottomBarContentPadding
                )
            }

            composable<Destination.OssLicenseDetail> {
                val viewModel = hiltViewModel<OssLicenseDetailViewModel>()
                OssLicenseDetailScreen(
                    viewModel = viewModel,
                    onNavigateUp = { appState.navigateUp() },
                    bottomBarContentPadding = bottomBarContentPadding
                )
            }
        }
    }
}
