package sopt.teammotivoo.presentation.dummy

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sopt.teammotivoo.domain.entity.MotivooStorage
import sopt.teammotivoo.domain.repository.DummyRepository
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(
    private val dummyRepository: DummyRepository,
    private val motivooStorage: MotivooStorage
) : ViewModel()
