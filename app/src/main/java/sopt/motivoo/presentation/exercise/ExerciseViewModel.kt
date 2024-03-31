package sopt.motivoo.presentation.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sopt.motivoo.data.service.ExerciseService
import sopt.motivoo.domain.entity.exercise.ExerciseData
import sopt.motivoo.util.UiState
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseService: ExerciseService,
) : ViewModel() {

    private val _exerciseHistoryInfo = MutableLiveData<UiState<ExerciseData>>(UiState.Empty)
    val exerciseHistoryInfo: LiveData<UiState<ExerciseData>> = _exerciseHistoryInfo

    fun getExerciseHistoryInfo() {
        viewModelScope.launch {
            _exerciseHistoryInfo.value = UiState.Loading
            kotlin.runCatching {
                exerciseService.getExerciseHistoryInfo()
            }.onSuccess {
                _exerciseHistoryInfo.value = UiState.Success(it.toExerciseData())
            }.onFailure {
                _exerciseHistoryInfo.value = UiState.Failure(it.message.toString())
            }
        }
    }
}
