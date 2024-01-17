package sopt.motivoo.presentation.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sopt.motivoo.data.service.ExerciseService
import sopt.motivoo.domain.entity.exercise.ExerciseInfo
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseService: ExerciseService,
) : ViewModel() {

    private val _exerciseHistoryInfo: MutableLiveData<List<ExerciseInfo>> = MutableLiveData()
    val exerciseHistoryInfo: LiveData<List<ExerciseInfo>> = _exerciseHistoryInfo

    fun getExerciseHistoryInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                exerciseService.getExerciseHistoryInfo()
            }.onSuccess {
                _exerciseHistoryInfo.value = it.toExerciseInfo()
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }
}