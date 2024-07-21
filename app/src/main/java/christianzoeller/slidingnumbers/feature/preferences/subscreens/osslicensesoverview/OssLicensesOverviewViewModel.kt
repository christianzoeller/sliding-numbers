package christianzoeller.slidingnumbers.feature.preferences.subscreens.osslicensesoverview

import androidx.lifecycle.ViewModel
import christianzoeller.slidingnumbers.repository.OssLicensesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OssLicensesOverviewViewModel @Inject constructor(
    ossLicensesRepository: OssLicensesRepository
) : ViewModel() {
    private val _state = MutableStateFlow<OssLicensesOverviewState>(OssLicensesOverviewState.Loading)
    val state = _state.asStateFlow()

    init {
        _state.value = when (val info = ossLicensesRepository.ossLicenseInfo) {
            null -> OssLicensesOverviewState.Error
            else -> OssLicensesOverviewState.Data(info)
        }
    }
}