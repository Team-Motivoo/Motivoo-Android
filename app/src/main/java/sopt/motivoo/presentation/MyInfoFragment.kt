package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.data.service.MyPageService
import sopt.motivoo.databinding.FragmentMypageMyinfoBinding
import sopt.motivoo.presentation.mypage.MyPageViewModel
import sopt.motivoo.util.binding.BindingFragment

class MyInfoFragment :
    BindingFragment<FragmentMypageMyinfoBinding>(R.layout.fragment_mypage_myinfo) {

    private val myPageViewModel by viewModels<MyPageViewModel>()
    lateinit var myPageService: MyPageService
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPageViewModel.getUserInfo()
        binding.tvMyinfoUserName.text = myPageViewModel.myPageResult.value?.userNickname
        binding.tvMyinfoUserAge.text = myPageViewModel.myPageResult.value?.userAge.toString()
        clickButtons()
    }

    private fun clickButtons() {
        binding.clMyinfoOut.setOnClickListener {
            navigateToMyServiceOut()
        }
        binding.tvMyinfoBack.setOnClickListener {
            navigateToMyPage()
        }
        binding.clMyinfoLogout.setOnClickListener {
            navigateToMyLogout()
        }
    }

    private fun navigateToMyServiceOut() {
        findNavController().navigate(R.id.action_myInfoFragment_to_myServiceOut)
    }

    private fun navigateToMyPage() {
        findNavController().navigate(R.id.action_myInfoFragment_to_myPageFragment)
    }

    private fun navigateToMyLogout() {
        findNavController().navigate(R.id.action_myInfoFragment_to_myLogout)
    }
}
