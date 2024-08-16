package christianzoeller.slidingnumbers.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Game : Destination

    @Serializable
    data object ResultsOverview : Destination

    @Serializable
    data class ResultDetail(val resultId: Long) : Destination

    @Serializable
    data object Preferences : Destination

    @Serializable
    data object OssLicensesOverview : Destination

    @Serializable
    data class OssLicenseDetail(
        val libraryName: String,
        val libraryId: String,
        val licenses: List<String>
    ) : Destination
}
