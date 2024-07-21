package christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensedetail

import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.License

sealed interface OssLicenseDetailState {
    val libraryName: String

    data class Loading(
        override val libraryName: String
    ) : OssLicenseDetailState

    data class Data(
        override val libraryName: String,
        val library: Library,
        val licenses: List<License>
    ) : OssLicenseDetailState

    data class Error(
        override val libraryName: String
    ) : OssLicenseDetailState
}