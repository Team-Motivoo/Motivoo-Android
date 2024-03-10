package sopt.motivoo.presentation.invitecode

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentPostInviteCodeBinding
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.drawableOf
import sopt.motivoo.util.extension.setOnSingleClickListener
import sopt.motivoo.util.extension.setVisible
import sopt.motivoo.util.findStartDestination

@AndroidEntryPoint
class PostInviteCodeFragment :
    BindingFragment<FragmentPostInviteCodeBinding>(R.layout.fragment_post_invite_code) {

    private val inviteCodeViewModel by viewModels<PostInviteCodeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inviteCodeViewModel = inviteCodeViewModel

        collectData()
        clickBackButton()
        clickDoneButton()
    }

    private fun clickDoneButton() {
        binding.btnPostInviteCodeDone.setOnSingleClickListener {
            inviteCodeViewModel.postInviteCode()
        }
    }

    private fun clickBackButton() {
        binding.includePostInviteCodeToolbar.tvToolbarBack.setOnSingleClickListener { findNavController().popBackStack() }
    }

    private fun collectData() {
        inviteCodeViewModel.isPostInviteCodeEmpty.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .distinctUntilChanged()
            .onEach { isEmpty ->
                when (isEmpty) {
                    true -> setDefaultUi()
                    false -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        inviteCodeViewModel.postInviteCodeState.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .distinctUntilChanged()
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        inviteCodeViewModel.resetOnboardingState()
                        val navController = findNavController()
                        val startDestinationId = navController.findStartDestination().id
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo(startDestinationId, true)
                            .build()

                        findNavController().navigate(
                            R.id.action_postInviteCodeFragment_to_homeFragment,
                            null,
                            navOptions
                        )
                    }

                    is UiState.Failure -> {
                        binding.etPostInviteCode.background =
                            requireContext().drawableOf(R.drawable.shape_edittext_error_radius8)
                        binding.tvPostInviteCodeErrorMessage.setVisible(VISIBLE)
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setDefaultUi() {
        binding.etPostInviteCode.background =
            requireContext().drawableOf(R.drawable.selector_edittext_input)
        binding.tvPostInviteCodeErrorMessage.setVisible(GONE)
    }
}
