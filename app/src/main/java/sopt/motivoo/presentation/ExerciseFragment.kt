package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentExerciseBinding
import sopt.motivoo.domain.entity.exercise.ExerciseData
import sopt.motivoo.presentation.exercise.ExerciseAdapter
import sopt.motivoo.presentation.exercise.ExerciseViewModel
import sopt.motivoo.util.binding.BindingFragment

@AndroidEntryPoint
class ExerciseFragment : BindingFragment<FragmentExerciseBinding>(R.layout.fragment_exercise) {

    private val exerciseViewModel by viewModels<ExerciseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exerciseViewModel.getExerciseHistoryInfo()
        setClickEvents()
        observeLiveData()
    }

    private fun setClickEvents() {
        binding.tvExerciseEmptyBtn.setOnClickListener {
            findNavController().navigate(R.id.action_exerciseFragment_to_homeFragment)
        }
    }

    private fun observeLiveData() {
        exerciseViewModel.exerciseHistoryInfo.observe(viewLifecycleOwner) { response ->
            initViews(response)
        }
    }

    private fun initViews(exerciseData: ExerciseData) {
        setViewsVisibility(exerciseData)
    }

    private fun setViewsVisibility(exerciseData: ExerciseData) {
        if (exerciseData.exerciseItemInfoList.isEmpty()) {
            setRecyclerViewVisibility(false)
            setEmptyViewVisibility(true)
            initAdapter(exerciseData)
        } else {
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
        const val CHILD = "자식"
    }
}
