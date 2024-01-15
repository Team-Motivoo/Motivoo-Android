package sopt.motivoo.presentation.invitecode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentPostInviteCodeBinding
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.drawableOf
import sopt.motivoo.util.extension.setOnSingleClickListener
import sopt.motivoo.util.extension.setVisible

class PostInviteCodeFragment :
    BindingFragment<FragmentPostInviteCodeBinding>(R.layout.fragment_post_invite_code) {

    private val inviteCodeViewModel by activityViewModels<InviteCodeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inviteCodeViewModel = inviteCodeViewModel

        collectData()
        clickBackButton()
    }

    private fun clickBackButton() {
        binding.includePostInviteCodeToolbar.tvToolbarBack.setOnSingleClickListener { findNavController().popBackStack() }
    }

    private fun collectData() {
        inviteCodeViewModel.isValidCode.flowWithLifecycle(lifecycle).onEach { isValidCode ->
            when (isValidCode) {
                null -> {
                    setDefaultUi()
                    binding.btnPostInviteCodeDone.isEnabled = false
                }

                true -> {
                    setDefaultUi()
                    binding.btnPostInviteCodeDone.isEnabled = true
                }

                false -> {
                    binding.etPostInviteCode.background =
                        requireContext().drawableOf(R.drawable.shape_edittext_error_radius8)
                    binding.tvPostInviteCodeErrorMessage.setVisible(View.VISIBLE)
                    binding.btnPostInviteCodeDone.isEnabled = false
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun setDefaultUi() {
        binding.etPostInviteCode.background =
            requireContext().drawableOf(R.drawable.selector_edittext_input)
        binding.tvPostInviteCodeErrorMessage.setVisible(View.GONE)
    }
}
