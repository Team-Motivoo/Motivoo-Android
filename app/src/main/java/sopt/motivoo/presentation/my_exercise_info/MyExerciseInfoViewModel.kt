package sopt.motivoo.presentation.my_exercise_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sopt.motivoo.data.service.MyExerciseInfoService
import sopt.motivoo.domain.entity.MyExerciseInfo
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyExerciseInfoViewModel @Inject constructor(
    private val myExerciseInfoService: MyExerciseInfoService,
) : ViewModel() {
    private val _myExerciseInfo: MutableLiveData<MyExerciseInfo> =
        MutableLiveData()
    val myExerciseInfo: LiveData<MyExerciseInfo> = _myExerciseInfo
    fun getMyExerciseInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                myExerciseInfoService.getMyExerciseInfo()
            }.onSuccess {
                _myExerciseInfo.value = MyExerciseInfo(
                    it.data.isExercise,
                    it.data.exerciseType,
                    it.data.exerciseFrequency,
                    it.data.exerciseTime,
                    it.data.healthNotes
                )

            }.onFailure {
                Timber.e(it.message)
            }
        }
    }
}