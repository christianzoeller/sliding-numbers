package christianzoeller.slidingnumbers.repository

import android.content.Context
import android.util.Log
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.util.parseData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import javax.inject.Inject

typealias OssLicenseInfo = Libs

private const val librariesAssetsFile = "aboutlibraries.json"

class OssLicensesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    var ossLicenseInfo: OssLicenseInfo? = null
        private set

    init {
        ossLicenseInfo = loadLicenseInformation()
    }

    private fun loadLicenseInformation() =
        try {
            context.assets
                .open(librariesAssetsFile)
                .bufferedReader()
                .use { reader ->
                    val fileContent = reader.readText()
                    val parsed = parseData(fileContent)
                    OssLicenseInfo(
                        libraries = parsed.libraries
                            .sortedBy { it.name.lowercase() }
                            .toImmutableList(),
                        licenses = parsed.licenses.toImmutableSet()
                    )
                }
        } catch (e: Exception) {
            Log.e("SN", "Failed to parse OSS license information: $e")
            null
        }
}