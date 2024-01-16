package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageMyinfoBinding
import sopt.motivoo.util.binding.BindingFragment

class MyInfoFragment :
    BindingFragment<FragmentMypageMyinfoBinding>(R.layout.fragment_mypage_myinfo) {
    private val args: MyInfoFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSetText()
        clickButtons()
    }

    private fun initSetText() {
        binding.tvMyinfoUserName.text = args.userNickname
        binding.tvMyinfoUserAge.text = args.userAge.toString()
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
