package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageBinding
import sopt.motivoo.presentation.mypage.MyPageViewModel
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setVisible
import kotlin.properties.Delegates

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val myPageViewModel by viewModels<MyPageViewModel>()
    private lateinit var userNickname: String
    private var userAge by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPageViewModel.getUserInfo()
        observeLiveData()
        clickButtons()
    }

    private fun observeLiveData() {
        myPageViewModel.myPageUserInfo.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    binding.tvMypageName.text = uiState.data.userNickname
                    binding.clMypageRoot.setVisible(VISIBLE)
                    binding.tvMypageMyInfo.setOnClickListener {
                        navigateToMyInfo()
                    }
                }

                is UiState.Loading -> {
                    binding.clMypageRoot.setVisible(GONE)
                }

                else -> Unit
            }
        }
    }

    private fun clickButtons() {

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
        val sendUserInfo = MyPageFragmentDirections.actionMyPageFragmentToMyInfoFragment(
            userNickname, userAge
        )
        findNavController().navigate(sendUserInfo)
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
