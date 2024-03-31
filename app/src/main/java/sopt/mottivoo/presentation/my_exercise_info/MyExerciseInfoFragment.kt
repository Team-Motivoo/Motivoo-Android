package sopt.mottivoo.presentation.my_exercise_info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.mottivoo.R
import sopt.mottivoo.databinding.FragmentMypageExerciseInfoBinding
import sopt.mottivoo.util.binding.BindingFragment

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
            findNavController().popBackStack(R.id.myPageFragment, false)
        }
    }
}
