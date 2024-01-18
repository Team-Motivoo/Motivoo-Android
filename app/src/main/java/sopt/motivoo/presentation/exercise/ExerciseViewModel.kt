package sopt.motivoo.presentation.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sopt.motivoo.data.service.ExerciseService
import sopt.motivoo.domain.entity.exercise.ExerciseData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseService: ExerciseService,
) : ViewModel() {

    private val _exerciseHistoryInfo: MutableLiveData<ExerciseData> =
        MutableLiveData()
    val exerciseHistoryInfo: LiveData<ExerciseData> = _exerciseHistoryInfo

    fun getExerciseHistoryInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                exerciseService.getExerciseHistoryInfo()
            }.onSuccess {
                _exerciseHistoryInfo.value = it.toExerciseData()
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }
}
