package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageBinding
import sopt.motivoo.util.binding.BindingFragment

class MyPageFragment : BindingFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickButtons()
    }

    private fun clickButtons() {
        binding.tvMypageMyInfo.setOnClickListener {
            navigateToMyInfo()
        }

        binding.clMypageExerciseInfo.setOnClickListener {
            navigateToMyExerciseInfo()
        }

        binding.clMypageServiceUse.setOnClickListener {
            navigateToServiceUseWebView()
        }

        binding.clMypagePrivacyPolicy.setOnClickListener {
            navigateToPrivacyPolicyWebView()
        }

        binding.clMypageOpenSource.setOnClickListener {
            navigateToOpenSourceWebView()
        }

        binding.clMypageDeveloperInfo.setOnClickListener {
            navigateToDeveloperInfoWebView()
        }

        binding.clMypageAskKakao.setOnClickListener {
            navigateToAskKakaoWebView()
        }
    }

    private fun navigateToMyInfo() {
        findNavController().navigate(R.id.action_myPageFragment_to_myInfoFragment)
    }

    private fun navigateToMyExerciseInfo() {
        findNavController().navigate(R.id.action_myPageFragment_to_myExerciseInfoFragment)
    }

    private fun navigateToServiceUseWebView() {
        val action =
            MyPageFragmentDirections.actionMyPageFragmentToWebViewFragment(getString(R.string.service_use_url))
        findNavController().navigate(action)
    }

    private fun navigateToPrivacyPolicyWebView() {
        val action =
            MyPageFragmentDirections.actionMyPageFragmentToWebViewFragment(getString(R.string.privacy_policy_url))
        findNavController().navigate(action)
    }

    private fun navigateToOpenSourceWebView() {
        val action =
            MyPageFragmentDirections.actionMyPageFragmentToWebViewFragment(getString(R.string.open_source_url))
        findNavController().navigate(action)
    }

    private fun navigateToDeveloperInfoWebView() {
        val action =
            MyPageFragmentDirections.actionMyPageFragmentToWebViewFragment(getString(R.string.developer_info_url))
        findNavController().navigate(action)
    }

    private fun navigateToAskKakaoWebView() {
        val action =
            MyPageFragmentDirections.actionMyPageFragmentToWebViewFragment(getString(R.string.ask_kakao_url))
        findNavController().navigate(action)
    }
}
