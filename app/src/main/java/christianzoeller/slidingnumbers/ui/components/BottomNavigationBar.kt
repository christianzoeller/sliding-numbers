package christianzoeller.slidingnumbers.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import christianzoeller.slidingnumbers.navigation.BottomNavigationDestination
import christianzoeller.slidingnumbers.navigation.NavigationHandler
import christianzoeller.slidingnumbers.navigation.NoOpNavigationHandler
import christianzoeller.slidingnumbers.navigation.bottomNavigationDestinations
import christianzoeller.slidingnumbers.ui.theme.SlidingNumbersTheme
import christianzoeller.slidingnumbers.ui.tooling.CompactPreview

@Composable
fun BottomNavigationBar(
    navigationHandler: NavigationHandler,
    destinations: List<BottomNavigationDestination> = bottomNavigationDestinations
) {
    val selectedTab = navigationHandler.selectedTab.collectAsStateWithLifecycle()

    NavigationBar {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = destination == selectedTab.value,
                onClick = { navigationHandler.navigate(destination) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.navigationIcon),
                        contentDescription = stringResource(id = destination.navigationIconDescription)
                    )
                },
                label = { Text(text = stringResource(id = destination.labelId)) }
            )
        }
    }
}

@CompactPreview
@Composable
private fun BottomNavigationBar_Preview() = SlidingNumbersTheme {
    BottomNavigationBar(navigationHandler = NoOpNavigationHandler)
}