package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageBinding
import sopt.motivoo.presentation.mypage.MyPageViewModel
import sopt.motivoo.util.binding.BindingFragment

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val myPageViewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPageViewModel.getUserInfo()
        observingLiveData()
        clickButtons()
    }

    private fun observingLiveData() {
        myPageViewModel.myPageUserInfo.observe(viewLifecycleOwner) {
            binding.tvMypageName.text = it.userNickname
            val sendUserInfo = MyPageFragmentDirections.actionMyPageFragmentToMyInfoFragment(
                it.userNickname,
                it.userAge
            )
            Navigation.findNavController(requireView()).navigate(sendUserInfo)
        }
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
