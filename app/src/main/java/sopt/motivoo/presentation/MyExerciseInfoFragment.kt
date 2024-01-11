package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentMypageExerciseInfoBinding
import sopt.motivoo.util.binding.BindingFragment

class MyExerciseInfoFragment :
    BindingFragment<FragmentMypageExerciseInfoBinding>(R.layout.fragment_mypage_exercise_info) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickButtons()
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
