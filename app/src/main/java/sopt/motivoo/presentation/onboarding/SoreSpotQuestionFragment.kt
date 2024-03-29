package sopt.motivoo.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
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
import sopt.motivoo.databinding.FragmentSoreSpotQusetionBinding
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.presentation.type.DoExerciseType
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setOnSingleClickListener
import sopt.motivoo.util.findStartDestination
import javax.inject.Inject

@AndroidEntryPoint
class SoreSpotQuestionFragment :
    BindingFragment<FragmentSoreSpotQusetionBinding>(R.layout.fragment_sore_spot_qusetion) {

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    @Inject
    lateinit var motivooStorage: MotivooStorage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onboardingViewModel = onboardingViewModel

        setupDoneButton()
    }

    private fun setupDoneButton() {
        binding.btnOnboardingDone.setOnSingleClickListener {
            onboardingViewModel.postOnboardingInfo(
                getUserTypeString(),
                getWhatExerciseTypeString(),
                getWhatActivityTypeString(),
                getFrequencyTypeString(),
                getTimeTypeString(),
                isDoExerciseSelected(),
                getSelectedSoreSpotString()
            )
            collectData()
        }
    }

    private fun collectData() {
        onboardingViewModel.isPostOnboardingInfoSuccess.flowWithLifecycle(
            viewLifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
            .distinctUntilChanged()
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        binding.pvLoading.visibility = View.GONE
                        moveToNextFragment()
                    }

                    is UiState.Loading -> {
                        binding.pvLoading.visibility = View.VISIBLE
                    }

                    else -> {
                        binding.pvLoading.visibility = View.GONE
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun getUserTypeString() =
        onboardingViewModel.userType.value?.titleRes?.let { getString(it) }.orEmpty()

    private fun getWhatExerciseTypeString() =
        onboardingViewModel.whatExerciseType.value?.titleRes?.let { getString(it) }.orEmpty()

    private fun getWhatActivityTypeString() =
        onboardingViewModel.whatActivityType.value?.titleRes?.let { getString(it) }.orEmpty()

    private fun getFrequencyTypeString() =
        onboardingViewModel.frequencyType.value?.titleRes?.let { getString(it) }.orEmpty()

    private fun getTimeTypeString() =
        onboardingViewModel.timeType.value?.titleRes?.let { getString(it) }.orEmpty()

    private fun isDoExerciseSelected() =
        onboardingViewModel.doExerciseType.value == DoExerciseType.YES

    private fun getSelectedSoreSpotString(): List<String> {
        return onboardingViewModel.soreSpotFilterType.value
            .filter { it.value }
            .keys
            .map { getString(it.titleRes) }
    }

    private fun moveToNextFragment() {
        val navController = findNavController()
        val startDestinationId = navController.findStartDestination().id
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestinationId, true)
            .build()

        findNavController().navigate(
            R.id.action_soreSpotQuestionFragment_to_startMotivooFragment,
            null,
            navOptions
        )
    }
}
