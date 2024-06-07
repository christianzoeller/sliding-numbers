package christianzoeller.slidingnumbers

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import christianzoeller.slidingnumbers.feature.game.GameScreen
import christianzoeller.slidingnumbers.feature.game.GameViewModel

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "game") {
        composable("game") {
            val viewModel = hiltViewModel<GameViewModel>()
            GameScreen(viewModel)
        }
    }
}