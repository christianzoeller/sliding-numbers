package christianzoeller.slidingnumbers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import christianzoeller.slidingnumbers.navigation.SlidingNumbersNavHost
import christianzoeller.slidingnumbers.navigation.TopLevelDestination

@Composable
fun SlidingNumbersApp(
    appState: SlidingNumbersAppState,
    modifier: Modifier = Modifier
) {
    val currentDestination = appState.currentDestination

    Scaffold(
        bottomBar = {
            NavigationBar {
                appState.topLevelDestinations.forEach {
                    NavigationBarItem(
                        selected = currentDestination?.isTopLevelDestinationInHierarchy(it) ?: false,
                        onClick = { appState.navigateToTopLevelDestination(it) },
                        icon = {
                            Icon(
                                painter = painterResource(id = it.icon),
                                contentDescription = stringResource(id = it.iconDescription)
                            )
                        },
                        label = { Text(text = stringResource(id = it.label)) }
                    )
                }
            }
        },
        modifier = modifier
    ) { contentPadding ->
        val bottomBarContentPadding = PaddingValues(
            bottom = contentPadding.calculateBottomPadding()
        )

        SlidingNumbersNavHost(
            appState = appState,
            bottomBarContentPadding = bottomBarContentPadding
        )
    }
}

private fun NavDestination.isTopLevelDestinationInHierarchy(
    topLevelDestination: TopLevelDestination
) = hierarchy.any { destination ->
    destination.hasRoute(topLevelDestination::class)
}