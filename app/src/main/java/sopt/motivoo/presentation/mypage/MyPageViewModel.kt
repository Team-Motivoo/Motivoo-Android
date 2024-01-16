package sopt.motivoo.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sopt.motivoo.data.service.MyPageService
import sopt.motivoo.domain.entity.mypage.UserInfo
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageService: MyPageService,
) : ViewModel() {


    private val _myPageResult: MutableLiveData<UserInfo> = MutableLiveData()
    val myPageResult: LiveData<UserInfo> = _myPageResult

    fun getUserInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                myPageService.getUserInfo()
            }.onSuccess {
                _myPageResult.value = UserInfo(it.data.userNickname, it.data.userAge, it.data.userType)
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }

}