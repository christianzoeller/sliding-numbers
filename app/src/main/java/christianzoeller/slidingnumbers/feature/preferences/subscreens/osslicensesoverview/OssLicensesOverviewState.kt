package christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensesoverview

import christianzoeller.slidingnumbers.repository.OssLicenseInfo

sealed interface OssLicensesOverviewState {
    data object Loading : OssLicensesOverviewState
    data class Data(val ossLicenseInfo: OssLicenseInfo) : OssLicensesOverviewState
    data object Error : OssLicensesOverviewState
}