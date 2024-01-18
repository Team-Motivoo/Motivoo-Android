package sopt.motivoo.presentation.home

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sopt.motivoo.data.model.request.home.RequestMissionImageDto
import sopt.motivoo.data.model.request.home.RequestMissionTodayDto
import sopt.motivoo.domain.entity.home.HomeData
import sopt.motivoo.domain.entity.home.MissionChoiceData
import sopt.motivoo.domain.entity.home.MissionImageData
import sopt.motivoo.domain.repository.HomeRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
) : ViewModel() {
    val stepCount = MutableLiveData<Int>()
    val imageBitmap = MutableLiveData<Bitmap>()

    private val _homeData = MutableLiveData<HomeData>()
    val homeData: LiveData<HomeData> = _homeData

    private val _missionChoiceData = MutableLiveData<MissionChoiceData>()
    val missionChoiceData: LiveData<MissionChoiceData> = _missionChoiceData

    private val _otherStepCount = MutableLiveData<Long>()
    val otherStepCount: LiveData<Long> = _otherStepCount

    private val _imageData = MutableLiveData<MissionImageData>()
    val imageData: LiveData<MissionImageData> = _imageData

    private val _isUploadImage = MutableLiveData<Boolean>()
    val isUploadImage: LiveData<Boolean> = _isUploadImage

    fun getEventOtherStepCount(otherUid: Long) {
        repository.getEventOtherStepCount(otherUid) {
            _otherStepCount.value = it
        }
    }

    fun setMyStepCount(myUid: Long, stepCount: Int) {
        repository.setMyStepCount(myUid, stepCount)
        setStepCount(stepCount)
    }

    fun setStepCount(stepCount: Int) {
        this.stepCount.value = stepCount
    }

    fun setOtherStepCount(stepCount: Int) {
        _otherStepCount.value = stepCount.toLong()
    }

    fun setImageBitmap(imageBitmap: Bitmap) {
        this.imageBitmap.value = imageBitmap
    }

    fun patchHome(myStepCount: Int, otherStepCount: Int) {
        viewModelScope.launch {
            repository.patchHome(myStepCount, otherStepCount).onSuccess {
                _homeData.value = it
            }.onFailure { }
        }
    }

    fun postMissionTodayChoice() {
        viewModelScope.launch {
            repository.postMissionTodayChoice().onSuccess {
                _missionChoiceData.value = it
            }.onFailure { }
        }
    }

    fun postMissionToday(missionId: Long) {
        viewModelScope.launch {
            repository.postMissionToday(RequestMissionTodayDto(missionId = missionId)).onSuccess {
                postMissionTodayChoice()
            }
        }
    }

    fun patchMissionImage(imagePrefix: String) {
        viewModelScope.launch {
            repository.patchMissionImage(RequestMissionImageDto(imagePrefix)).onSuccess {
                _imageData.value = it
            }.onFailure { }
        }
    }

    fun uploadPhoto(url: String, bitmap: Bitmap) {
        viewModelScope.launch {
            repository.uploadPhoto(url, bitmap).onSuccess {
                _isUploadImage.value = true
            }.onFailure {
                _isUploadImage.value = false
            }
        }
    }
}
