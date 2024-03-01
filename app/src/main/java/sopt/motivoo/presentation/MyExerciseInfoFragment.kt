package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageExerciseInfoBinding
import sopt.motivoo.presentation.my_exercise_info.MyExerciseInfoViewModel
import sopt.motivoo.util.binding.BindingFragment

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
        with(binding) {
            myExerciseInfoViewModel.myExerciseInfo.observe(viewLifecycleOwner) {
                if (it.isExercise) {
                    tvExerciseInfoOrNotAnswer.text = getString(R.string.yes)
                } else {
                    tvExerciseInfoOrNotAnswer.text = getString(R.string.no)
                }
                binding.tvExerciseInfoIntensityAnswer.text = it.exerciseType
                binding.tvExerciseInfoAverageCountAnswer.text = it.exerciseFrequency
                binding.tvExerciseInfoAverageTimeAnswer.text = it.exerciseTime
                binding.tvExerciseInfoWatchOutAnswer.text = it.healthNotes.joinToString(", ")
            }
        }
    }

    private fun clickButtons() {
        binding.tvExerciseInfoBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
