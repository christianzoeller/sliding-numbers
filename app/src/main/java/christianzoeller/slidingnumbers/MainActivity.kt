package christianzoeller.slidingnumbers

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import christianzoeller.slidingnumbers.repository.PreferencesRepository
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    private var darkTheme by mutableStateOf<Boolean?>(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                preferencesRepository.uiModeFlow.collect {
                    darkTheme = when (it) {
                        Configuration.UI_MODE_NIGHT_NO -> false
                        Configuration.UI_MODE_NIGHT_YES -> true
                        else -> null
                    }
                }
            }
        }
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
        )
        setContent {
            val appState = rememberSlidingNumbersAppState()

            SlidingNumbersTheme(
                darkTheme = darkTheme ?: isSystemInDarkTheme()
            ) {
                SlidingNumbersApp(appState = appState)
            }
        }
    }
}
