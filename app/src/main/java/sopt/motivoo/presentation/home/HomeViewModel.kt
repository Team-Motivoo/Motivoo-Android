package sopt.motivoo.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sopt.motivoo.domain.entity.MotivooStorage
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pref: MotivooStorage
): ViewModel() {

}
