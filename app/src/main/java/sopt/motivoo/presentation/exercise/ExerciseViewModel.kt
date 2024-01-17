package sopt.motivoo.presentation.exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sopt.motivoo.data.service.ExerciseService
import sopt.motivoo.domain.entity.ExerciseInfo
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseService: ExerciseService,
) : ViewModel() {

    private val _exerciseHistoryInfo: MutableLiveData<ExerciseInfo> = MutableLiveData()
    val exerciseHistoryInfo: LiveData<ExerciseInfo> = _exerciseHistoryInfo

    fun getExerciseHistoryInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                exerciseService.getExerciseHistoryInfo()
            }.onSuccess {
                Timber.e(it.message)
/*                _exerciseHistoryInfo.value = ExerciseInfo(
                    it.data.userType,
                    it.data.todayMission.missionContent,
                    it.data.missionHistory.map { mission ->
                        ExerciseInfo.MissionHistory(
                            mission.date,
                            mission.myMissionContent,
                            mission.myMissionImgUrl,
                            mission.myMissionStatus,
                            mission.opponentMissionContent,
                            mission.opponentMissionImgUrl,
                            mission.opponentMissionStatus
                        )
                    },
                )*/
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }
}