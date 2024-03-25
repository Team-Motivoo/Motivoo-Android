package sopt.motivoo.presentation.invitecode

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentGetInviteCodeBinding
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener
import javax.inject.Inject

@AndroidEntryPoint
class GetInviteCodeFragment :
    BindingFragment<FragmentGetInviteCodeBinding>(R.layout.fragment_get_invite_code) {

    private val getInviteCodeViewModel by viewModels<GetInviteCodeViewModel>()

    @Inject
    lateinit var motivooStorage: MotivooStorage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getInviteCodeViewModel = getInviteCodeViewModel
        setClipboard()
        collectData()
        checkMatching()
        clickBackButton()
        overrideOnBackPressed()
    }

    private fun overrideOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        )
    }

    private fun clickBackButton() {
        binding.includeGetInviteCodeToolbar.tvToolbarBack.setOnSingleClickListener {
            findNavController().popBackStack()
        }
    }

    private fun checkMatching() {
        binding.btnGetInviteCodeCheckMatching.setOnSingleClickListener {
            getInviteCodeViewModel.getMatchedResult()
        }
    }

    private fun collectData() {
        getInviteCodeViewModel.checkMatchState.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .distinctUntilChanged()
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        findNavController().navigate(R.id.action_getInviteCodeFragment_to_homeFragment)
                    }

                    is UiState.Failure -> {
                        setMatchingToastAnimation()
                    }

                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setClipboard() {
        val clipboard: ClipboardManager =
            requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val inviteCode = binding.tvGetInviteCode.text
        val clip = ClipData.newPlainText(INVITE_CODE, inviteCode)

        binding.btnGetInviteCodeCopy.setOnSingleClickListener {
            clipboard.setPrimaryClip(clip)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
                setClipBoardToastAnimation()
        }
    }

    private fun setMatchingToastAnimation() {
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        binding.tvGetInviteCodeMatchingWaiting.startAnimation(fadeIn)
        binding.tvGetInviteCodeMatchingWaiting.visibility = View.VISIBLE

        lifecycleScope.launch {
            delay(TWO_SECONDS)
            val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            binding.tvGetInviteCodeMatchingWaiting.startAnimation(fadeOut)
            binding.tvGetInviteCodeMatchingWaiting.visibility = View.GONE
        }
    }

    private fun setClipBoardToastAnimation() {
        val fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        binding.tvGetInviteCodeToast.startAnimation(fadeIn)
        binding.tvGetInviteCodeToast.visibility = View.VISIBLE

        lifecycleScope.launch {
            delay(TWO_SECONDS)
            val fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            binding.tvGetInviteCodeToast.startAnimation(fadeOut)
            binding.tvGetInviteCodeToast.visibility = View.GONE
        }
    }

    companion object {
        const val TWO_SECONDS = 2000L
        private const val INVITE_CODE = "invite_code"
    }
}
