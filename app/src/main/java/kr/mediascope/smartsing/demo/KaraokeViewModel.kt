package kr.mediascope.smartsing.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kr.mediascope.smartsing.demo.data.datasource.remote.SingItRemoteDataSourceImpl
import kr.mediascope.smartsing.demo.data.datasource.remote.Result
import kr.mediascope.smartsing.demo.data.model.remote.MrItem

/**
 * 노래방 목록 화면
 */
class KaraokeViewModel constructor(
    val singItRepository: SingItRemoteDataSourceImpl = SingItRemoteDataSourceImpl()
): ViewModel() {
    /** UI 상태 */
    private val _uiState = MutableStateFlow(MrListUiState())
    val uiState: StateFlow<MrListUiState> = _uiState.asStateFlow()

    /** MR 목록 */
    private val _mrList = MutableStateFlow<List<MrItem>>(emptyList())
    val mrList: StateFlow<List<MrItem>> = _mrList.asStateFlow()

    fun searchMr(keyword: String,
                 index: Int,
                 perpage: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }

            singItRepository.searchMr(keyword,
                index,
                perpage
            ).also { result ->
                when (result) {
                    is Result.Success -> {
                        _mrList.value = result.data.mr_list
                        _uiState.update {
                            it.copy(
                                moveToListTop = true,
                                isLoading = false
                            )
                        }
                    }
                    else -> {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }

    data class MrListUiState(
        val moveToListTop: Boolean = false,
        val isLoading: Boolean = false
    )
}