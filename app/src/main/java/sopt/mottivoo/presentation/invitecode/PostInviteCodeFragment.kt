package sopt.mottivoo.presentation.invitecode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sopt.mottivoo.R
import sopt.mottivoo.databinding.FragmentPostInviteCodeBinding
import sopt.mottivoo.util.UiState
import sopt.mottivoo.util.binding.BindingFragment
import sopt.mottivoo.util.extension.drawableOf
import sopt.mottivoo.util.extension.setOnSingleClickListener

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
                        findNavController().navigate(R.id.action_postInviteCodeFragment_to_homeFragment)
                    }

                    is UiState.Failure -> {
                        binding.etPostInviteCode.background =
                            requireContext().drawableOf(R.drawable.shape_edittext_error_radius8)
                        binding.tvPostInviteCodeErrorMessage.visibility = View.VISIBLE
                    }

                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setDefaultUi() {
        binding.etPostInviteCode.background =
            requireContext().drawableOf(R.drawable.selector_edittext_input)
        binding.tvPostInviteCodeErrorMessage.visibility = View.GONE
    }
}
