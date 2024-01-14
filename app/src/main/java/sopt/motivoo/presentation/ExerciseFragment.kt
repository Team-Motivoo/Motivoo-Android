package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import sopt.motivoo.R
import sopt.motivoo.data.model.ExerciseInfo
import sopt.motivoo.databinding.FragmentExerciseBinding
import sopt.motivoo.presentation.exercise.ExerciseInfoAdapter
import sopt.motivoo.util.binding.BindingFragment

class ExerciseFragment : BindingFragment<FragmentExerciseBinding>(R.layout.fragment_exercise) {

    private val mockExerciseList = listOf<ExerciseInfo>(
        ExerciseInfo.NoticeInfo(
            missionContent = "오늘의 미션 내용"
        ),
        ExerciseInfo.EachDateInfo(
            userType = "PARENT",
            date = "2024년 01월 15일",
            myMissionImgUrl = null,
            opponentMissionImgUrl = "![f15c9e2ce52a1735081be72dcec2e97d]",
            myMissionContent = "내 미션 내용",
            opponentMissionContent = "부모님 미션 내용",
            myMissionStatus = "진행중",
            opponentMissionStatus = "성공"
        ),
        ExerciseInfo.EachDateInfo(
            userType = "CHILD",
            date = "2024년 12월 30일",
            myMissionImgUrl = "f15c9e2ce52a1735081be72dcec2e97d",
            opponentMissionImgUrl = null,
            myMissionContent = "내 미션 내용",
            opponentMissionContent = "부모님 미션 내용",
            myMissionStatus = "성공",
            opponentMissionStatus = "실패"
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exerciseAdapter = ExerciseInfoAdapter()
        binding.rvExerciseEachDateExercise.adapter = exerciseAdapter
        exerciseAdapter.setExerciseList(mockExerciseList)
    }
}
