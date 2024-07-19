package christianzoeller.slidingnumbers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import christianzoeller.slidingnumbers.navigation.NavigationGraph
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SlidingNumbersTheme {
                NavigationGraph()
            }
        }
    }
}
