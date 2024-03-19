package sopt.motivoo.presentation.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageBinding
import sopt.motivoo.presentation.type.UserType
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setVisible

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val myPageViewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPageViewModel.getUserInfo()
        observeLiveData()
        clickButtons()
        overrideOnBackPressed()
    }

    private fun overrideOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.homeFragment)
                }
            }
        )
    }

    private fun observeLiveData() {
        myPageViewModel.myPageUserInfo.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    if (uiState.data.userType == getString(UserType.CHILD.titleRes)) {
                        binding.tvMypageNickname.text = getString(R.string.mypage_nickname_child)
                    } else {
                        binding.tvMypageNickname.text = getString(R.string.mypage_nickname)
                    }
                    binding.tvMypageName.text = uiState.data.userNickname
                    binding.clMypageRoot.setVisible(VISIBLE)
                }

                is UiState.Loading -> {
                    binding.clMypageRoot.setVisible(GONE)
                }

                else -> Unit
            }
        }
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

        binding.clMypageAskKakao.setOnClickListener {
            openKakaoChat()
        }
    }

    private fun navigateToMyInfo() {
        val userInfo = (myPageViewModel.myPageUserInfo.value as? UiState.Success)?.data
        val sendUserInfo = MyPageFragmentDirections.actionMyPageFragmentToMyInfoFragment(
            userNickname = userInfo?.userNickname ?: "",
            userAge = userInfo?.userAge ?: 0
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

    private fun openKakaoChat() {
        val kakaoOpenChatUrl = "https://open.kakao.com/o/sSCxapcg"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(kakaoOpenChatUrl))
        startActivity(intent)
    }
}
