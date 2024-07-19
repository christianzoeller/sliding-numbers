package christianzoeller.slidingnumbers.navigation

import kotlinx.coroutines.flow.MutableStateFlow

interface NavigationHandler {
    val selectedTab: MutableStateFlow<BottomNavigationDestination>

    fun navigate(action: NavigationAction)
    fun navigate(destination: NavigationDestination)
}

data object NoOpNavigationHandler : NavigationHandler {
    override val selectedTab = MutableStateFlow<BottomNavigationDestination>(NavigationDestination.Game)

    override fun navigate(action: NavigationAction) {}
    override fun navigate(destination: NavigationDestination) {}
}