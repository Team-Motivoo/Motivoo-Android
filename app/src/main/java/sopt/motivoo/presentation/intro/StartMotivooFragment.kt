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
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener
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
    }

    private fun clickButton() {
        binding.btnStartMotivoo.setOnSingleClickListener {
            startMotivooViewModel.getOnboardingFinished()
            startMotivooViewModel.isOnboardingFinished.flowWithLifecycle(lifecycle)
                .onEach { getOnboardingFinishedState ->
                    when (getOnboardingFinishedState) {
                        true ->
                            if (motivooStorage.isFinishedOnboarding) findNavController().navigate(R.id.action_startMotivooFragment_to_getInviteCodeFragment)
                            else findNavController().navigate(
                                R.id.action_startMotivooFragment_to_ageQuestionFragment
                            )

                        false -> TODO("서버통신 실패를 알리는 무언가 필요")
                    }
                    startMotivooViewModel.resetStartState()
                }.launchIn(lifecycleScope)
        }

        binding.btnPostInviteCode.setOnSingleClickListener {
            findNavController().navigate(R.id.action_startMotivooFragment_to_postInviteCodeFragment)
        }
    }
}
