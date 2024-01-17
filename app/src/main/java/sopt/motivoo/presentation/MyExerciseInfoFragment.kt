package sopt.motivoo.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageExerciseInfoBinding
import sopt.motivoo.presentation.my_exercise_info.MyExerciseInfoViewModel
import sopt.motivoo.util.binding.BindingFragment
import timber.log.Timber

@AndroidEntryPoint
class MyExerciseInfoFragment :
    BindingFragment<FragmentMypageExerciseInfoBinding>(R.layout.fragment_mypage_exercise_info) {
    private val myExerciseInfoViewModel by viewModels<MyExerciseInfoViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myExerciseInfoViewModel.getMyExerciseInfo()
        observeLiveData()

        clickButtons()
    }

    private fun observeLiveData() {
        myExerciseInfoViewModel.myExerciseInfo.observe(viewLifecycleOwner) {
            binding.tvExerciseInfoOrNotAnswer.text = it.isExercise.toString()
            binding.tvExerciseInfoIntensityAnswer.text = it.exerciseType
            binding.tvExerciseInfoAverageCountAnswer.text = it.exerciseFrequency
            binding.tvExerciseInfoAverageTimeAnswer.text = it.exerciseTime
            binding.tvExerciseInfoWatchOutAnswer.text = it.healthNotes.toString()
            Log.d("test",it.healthNotes.toString())
        }
    }

    private fun clickButtons() {
        binding.tvExerciseInfoBack.setOnClickListener {
            navigateToMyPage()
        }
    }

    private fun navigateToMyPage() {
        findNavController().navigate(R.id.action_myExerciseInfoFragment_to_myPageFragment)
    }
}
