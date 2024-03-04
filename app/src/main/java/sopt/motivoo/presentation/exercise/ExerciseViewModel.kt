package sopt.motivoo.presentation.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sopt.motivoo.data.service.ExerciseService
import sopt.motivoo.domain.entity.exercise.ExerciseData
import sopt.motivoo.domain.repository.NetworkRepository
import sopt.motivoo.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseService: ExerciseService,
    private val networkRepository: NetworkRepository,
) : ViewModel() {

    private val _exerciseHistoryInfoList = ArrayList<ExerciseData.ExerciseItemInfo>()
    val exerciseHistoryInfoList: MutableLiveData<ArrayList<ExerciseData.ExerciseItemInfo>> by lazy {
        MutableLiveData<ArrayList<ExerciseData.ExerciseItemInfo>>()
    }

    private val _exerciseHistoryInfo = MutableLiveData<UiState<ExerciseData>>(UiState.Loading)
    val exerciseHistoryInfo: LiveData<UiState<ExerciseData>> = _exerciseHistoryInfo

    fun getExerciseHistoryInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                networkRepository.setLoading(true)
                exerciseService.getExerciseHistoryInfo()
            }.onSuccess {
                networkRepository.setLoading(false)
                _exerciseHistoryInfo.value = UiState.Success(it.toExerciseData())
            }.onFailure {
                networkRepository.setLoading(false)
                Timber.e(it.message)
            }
        }
    }
}
