package sopt.motivoo.presentation.exercise

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentExerciseBinding
import sopt.motivoo.domain.entity.exercise.ExerciseData
import sopt.motivoo.util.UiState
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setVisible
import timber.log.Timber

@AndroidEntryPoint
class ExerciseFragment : BindingFragment<FragmentExerciseBinding>(R.layout.fragment_exercise) {

    private val exerciseViewModel by viewModels<ExerciseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exerciseViewModel.getExerciseHistoryInfo()
        setClickEvents()
        observeLiveData()
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

    private fun setClickEvents() {
        binding.tvExerciseEmptyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_exerciseFragment_to_homeFragment)
        }
    }

    private fun observeLiveData() {
        exerciseViewModel.exerciseHistoryInfo.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    Timber.tag("aaa").e("${uiState.data}")
                    initViews(uiState.data)
                    binding.clExerciseRoot.setVisible(VISIBLE)
                }

                is UiState.Loading -> {
                    Timber.tag("aaa").e("로딩")
                    binding.clExerciseRoot.setVisible(GONE)
                }

                else -> Unit
            }
        }
    }

    private fun initViews(exerciseData: ExerciseData) {
        setViewsVisibility(exerciseData)
    }

    private fun setViewsVisibility(exerciseData: ExerciseData) {
        if (exerciseData.exerciseItemInfoList.isEmpty()) {
            setRecyclerViewVisibility(false)
            setEmptyViewVisibility(true)
        } else {
            initAdapter(exerciseData)
            setRecyclerViewVisibility(true)
            setEmptyViewVisibility(false)
        }
    }

    private fun setRecyclerViewVisibility(isVisible: Boolean) {
        binding.rvExerciseEachDateExercise.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun setEmptyViewVisibility(isVisible: Boolean) {
        binding.ivExerciseEmptyImg.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.tvExerciseEmptyContent.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.tvExerciseEmptyBtn.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun initAdapter(exerciseData: ExerciseData) {
        val adapter = ExerciseAdapter(userType = exerciseData.userType)
        adapter.updateItemList(exerciseList = exerciseData.exerciseItemInfoList)
        binding.rvExerciseEachDateExercise.adapter = adapter
    }

    companion object {
        const val PARENT = "부모"
        const val CHILD = "자녀"
    }
}
