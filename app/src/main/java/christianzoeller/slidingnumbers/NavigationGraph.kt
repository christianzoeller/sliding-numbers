package christianzoeller.slidingnumbers

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import christianzoeller.slidingnumbers.feature.game.GameScreen
import christianzoeller.slidingnumbers.feature.game.GameViewModel
import christianzoeller.slidingnumbers.feature.results.details.ResultDetailScreen
import christianzoeller.slidingnumbers.feature.results.details.ResultDetailViewModel
import christianzoeller.slidingnumbers.feature.results.overview.ResultsOverviewScreen
import christianzoeller.slidingnumbers.feature.results.overview.ResultsOverviewViewModel

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Game.route
    ) {
        composable(Screen.Game.route) {
            val viewModel = hiltViewModel<GameViewModel>()
            GameScreen(navController, viewModel)
        }

        composable(Screen.Results.route) {
            val viewModel = hiltViewModel<ResultsOverviewViewModel>()
            ResultsOverviewScreen(navController, viewModel)
        }

        composable(Screen.ResultDetail.route) {
            val viewModel = hiltViewModel<ResultDetailViewModel>()
            ResultDetailScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}