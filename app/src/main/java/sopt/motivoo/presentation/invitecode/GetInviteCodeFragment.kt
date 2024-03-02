package sopt.motivoo.presentation.invitecode

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentGetInviteCodeBinding
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener
import sopt.motivoo.util.extension.setVisible
import sopt.motivoo.util.findStartDestination
import javax.inject.Inject

@AndroidEntryPoint
class GetInviteCodeFragment :
    BindingFragment<FragmentGetInviteCodeBinding>(R.layout.fragment_get_invite_code) {

    private val getInviteCodeViewModel by viewModels<GetInviteCodeViewModel>()

    @Inject
    lateinit var motivooStorage: MotivooStorage

    private val safeArgs: GetInviteCodeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClipboard()
        setInviteCode()
        collectData()
        checkMatching()
        clickBackButton()
        backPressed()
    }

    private fun backPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    setBackPressed()
                }
            }
        )
    }

    private fun clickBackButton() {
        binding.includeGetInviteCodeToolbar.tvToolbarBack.setOnSingleClickListener {
            setBackPressed()
        }
    }

    private fun setBackPressed() {
        if (motivooStorage.isFinishedOnboarding) {
            passedOnboardingClickBackButton()
        } else {
            afterOnboardingClickBackButton()
        }
    }

    private fun checkMatching() {
        binding.btnGetInviteCodeCheckMatching.setOnSingleClickListener {
            getInviteCodeViewModel.getInviteCode()
        }
    }

    private fun collectData() {
        getInviteCodeViewModel.checkMatchState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        val navController = findNavController()
                        val startDestinationId = navController.findStartDestination().id
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(startDestinationId, true)
                            .build()

                        findNavController().navigate(
                            R.id.action_getInviteCodeFragment_to_homeFragment,
                            null,
                            navOptions
                        )
                    }

                    is UiState.Failure -> {
                        setMatchingToastAnimation()
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setInviteCode() {
        binding.tvGetInviteCode.text = safeArgs.inviteCode.ifEmpty {
            motivooStorage.inviteCode
        }
    }

    private fun passedOnboardingClickBackButton() {
        findNavController().popBackStack()
    }

    private fun afterOnboardingClickBackButton() {
        val navController = findNavController()
        val startDestinationId = navController.findStartDestination().id
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestinationId, true)
            .build()

        findNavController().navigate(
            R.id.action_getInviteCodeFragment_to_startMotivooFragment,
            null,
            navOptions
        )
    }

    private fun setClipboard() {
        val clipboard: ClipboardManager =
            requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val inviteCode = safeArgs.inviteCode.ifEmpty {
            motivooStorage.inviteCode
        }
        val formattedText = getString(R.string.share_text_message, inviteCode)
        val clip = ClipData.newPlainText(SHARE_TEXT, formattedText)

        binding.btnGetInviteCodeCopy.setOnSingleClickListener {
            clipboard.setPrimaryClip(clip)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
                setClipBoardToastAnimation()
        }
    }

    private fun setMatchingToastAnimation() {
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        binding.tvGetInviteCodeMatchingWaiting.startAnimation(fadeIn)
        binding.tvGetInviteCodeMatchingWaiting.setVisible(VISIBLE)

        lifecycleScope.launch {
            delay(TWO_SECONDS)
            val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            binding.tvGetInviteCodeMatchingWaiting.startAnimation(fadeOut)
            binding.tvGetInviteCodeMatchingWaiting.setVisible(GONE)
        }
    }

    private fun setClipBoardToastAnimation() {
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        binding.tvGetInviteCodeToast.startAnimation(fadeIn)
        binding.tvGetInviteCodeToast.setVisible(VISIBLE)

        lifecycleScope.launch {
            delay(TWO_SECONDS)
            val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            binding.tvGetInviteCodeToast.startAnimation(fadeOut)
            binding.tvGetInviteCodeToast.setVisible(GONE)
        }
    }

    companion object {
        const val TWO_SECONDS = 2000L
        private const val SHARE_TEXT = "share_text"
    }
}
