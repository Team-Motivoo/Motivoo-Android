package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.data.service.MyPageService
import sopt.motivoo.databinding.FragmentMypageBinding
import sopt.motivoo.presentation.mypage.MyPageViewModel
import sopt.motivoo.util.binding.BindingFragment

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val myPageViewModel by viewModels<MyPageViewModel>()
    lateinit var myPageService: MyPageService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPageViewModel.getUserInfo()
        binding.tvMypageName.text = myPageViewModel.myPageResult.value?.userNickname
        clickButtons()
    }

    private fun clickButtons() {
        binding.tvMypageMyInfo.setOnClickListener {
            navigateToMyInfo()
        }

        binding.clMypageExerciseInfo.setOnClickListener {
            navigateToMyExerciseInfo()
        }
    }

    private fun navigateToMyInfo() {
        findNavController().navigate(R.id.action_myPageFragment_to_myInfoFragment)
    }

    private fun navigateToMyExerciseInfo() {
        findNavController().navigate(R.id.action_myPageFragment_to_myExerciseInfoFragment)
    }
}
