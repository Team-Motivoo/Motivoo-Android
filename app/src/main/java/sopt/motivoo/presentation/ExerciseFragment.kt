package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import sopt.motivoo.R
import sopt.motivoo.data.model.ExerciseInfo
import sopt.motivoo.databinding.FragmentExerciseBinding
import sopt.motivoo.presentation.Exercise.ExerciseInfoAdapter
import sopt.motivoo.util.binding.BindingFragment

class ExerciseFragment : BindingFragment<FragmentExerciseBinding>(R.layout.fragment_exercise) {

    private val mockExerciseList = listOf<ExerciseInfo>(
        ExerciseInfo.NoticeInfo(
            mission_content = "오늘의 미션 내용"
        ),
        ExerciseInfo.EachDateInfo(
            date = "2024/01/12", my_mission_img_url = null, opponent_mission_img_url = "![f15c9e2ce52a1735081be72dcec2e97d]",
            my_mission_content = "내 미션 내용", opponent_mission_content = "부모님 미션 내용",
            my_mission_status = "진행중", opponent_mission_status = "성공"
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exerciseAdapter = ExerciseInfoAdapter(requireContext())
        binding.rvExerciseEachDateExercise.adapter = exerciseAdapter
        exerciseAdapter.setExerciseList(mockExerciseList)
    }
}
