package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentExerciseBinding
import sopt.motivoo.domain.entity.ExerciseInfo
import sopt.motivoo.presentation.exercise.ExerciseAdapter
import sopt.motivoo.presentation.exercise.ExerciseViewModel
import sopt.motivoo.util.binding.BindingFragment

@AndroidEntryPoint
class ExerciseFragment : BindingFragment<FragmentExerciseBinding>(R.layout.fragment_exercise) {

    private val exerciseViewModel by viewModels<ExerciseViewModel>()

    private val mockExerciseList = listOf<ExerciseInfo>(
        ExerciseInfo.NoticeInfo(
            missionContent = "오늘의 미션 내용"
        ),
        ExerciseInfo.EachDateInfo(
            userType = "PARENT",
            date = "2024년 01월 15일",
            myMissionImgUrl = null,
            opponentMissionImgUrl = "https://github.com/Team-Motivoo/Motivoo-Android/assets/113780698/f5e4d978-115e-4c3d-82b3-f06b3bc334aa",
            myMissionContent = "내 미션 내용",
            opponentMissionContent = "부모님 미션 내용",
            myMissionStatus = "진행중",
            opponentMissionStatus = "성공"
        ),
        ExerciseInfo.EachDateInfo(
            userType = "CHILD",
            date = "2024년 12월 30일",
            myMissionImgUrl = "https://github.com/Team-Motivoo/Motivoo-Android/assets/113780698/f5e4d978-115e-4c3d-82b3-f06b3bc334aa",
            opponentMissionImgUrl = null,
            myMissionContent = "내 미션 내용",
            opponentMissionContent = "부모님 미션 내용",
            myMissionStatus = "성공",
            opponentMissionStatus = "실패"
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exerciseViewModel.getExerciseHistoryInfo()
        observeLiveData()


        binding.rvExerciseEachDateExercise.adapter = ExerciseAdapter()
        ExerciseAdapter().setExerciseList(mockExerciseList)
    }

    private fun observeLiveData() {
        exerciseViewModel.exerciseHistoryInfo.observe(viewLifecycleOwner) {
        }
    }
}
