package sopt.motivoo.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sopt.motivoo.data.service.MyPageService
import sopt.motivoo.domain.entity.mypage.UserInfo
import sopt.motivoo.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageService: MyPageService,
) : ViewModel() {

    private val _myPageResult = MutableLiveData<UiState<UserInfo>>(UiState.Loading)
    val myPageUserInfo: LiveData<UiState<UserInfo>> = _myPageResult

    fun getUserInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                myPageService.getUserInfo()
            }.onSuccess {
                _myPageResult.value = UiState.Success(it.toUserInfo())
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }
}
