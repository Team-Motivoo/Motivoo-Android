package sopt.motivoo.presentation.intro

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentStartMotivooBinding
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener
import sopt.motivoo.util.extension.showSnackbar
import javax.inject.Inject

@AndroidEntryPoint
class StartMotivooFragment :
    BindingFragment<FragmentStartMotivooBinding>(R.layout.fragment_start_motivoo) {

    private val startMotivooViewModel by viewModels<StartMotivooViewModel>()

    @Inject
    lateinit var motivooStorage: MotivooStorage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickButton()
        collectData()
    }

    private fun clickButton() {
        binding.btnStartMotivoo.setOnSingleClickListener {
            startMotivooViewModel.getOnboardingFinished()
        }

        binding.btnPostInviteCode.setOnSingleClickListener {
            findNavController().navigate(R.id.action_startMotivooFragment_to_postInviteCodeFragment)
        }
    }

    private fun collectData() {
        startMotivooViewModel.isOnboardingFinished.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        startMotivooViewModel.resetStartState()
                        if (motivooStorage.isFinishedOnboarding) {
                            val inviteCode = motivooStorage.inviteCode
                            val action =
                                StartMotivooFragmentDirections.actionStartMotivooFragmentToGetInviteCodeFragment(
                                    inviteCode
                                )
                            findNavController().navigate(action)
                        } else findNavController().navigate(
                            R.id.action_startMotivooFragment_to_ageQuestionFragment
                        )
                    }

                    else -> Unit
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
