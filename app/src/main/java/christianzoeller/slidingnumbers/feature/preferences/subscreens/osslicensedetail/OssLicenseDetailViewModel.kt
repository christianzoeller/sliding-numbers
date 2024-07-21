package christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import christianzoeller.slidingnumbers.navigation.NavigationDestination
import christianzoeller.slidingnumbers.repository.OssLicensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OssLicenseDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val ossLicensesRepository: OssLicensesRepository
) : ViewModel() {
    private val navArgs = savedStateHandle
        .toRoute<NavigationDestination.OssLicenseDetail>()

    private val _state = MutableStateFlow<OssLicenseDetailState>(
        OssLicenseDetailState.Loading(navArgs.libraryName)
    )
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        val library = ossLicensesRepository.getLibraryById(navArgs.libraryId)
        val licenses = navArgs.licenses.mapNotNull {
            ossLicensesRepository.getLicenseByName(it)
        }

        _state.value = when (library) {
            null -> OssLicenseDetailState.Error(navArgs.libraryName)
            else -> OssLicenseDetailState.Data(
                libraryName = library.name,
                library = library,
                licenses = licenses
            )
        }
    }
}