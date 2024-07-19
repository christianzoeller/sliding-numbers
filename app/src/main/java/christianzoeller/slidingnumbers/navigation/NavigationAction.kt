package christianzoeller.slidingnumbers.navigation

sealed interface NavigationAction {
    data object Up : NavigationAction
    data class Navigate(val destination: NavigationDestination) : NavigationAction
}