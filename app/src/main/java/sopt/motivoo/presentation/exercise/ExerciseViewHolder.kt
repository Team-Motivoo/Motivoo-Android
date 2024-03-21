package sopt.motivoo.presentation.exercise

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import sopt.motivoo.R
import sopt.motivoo.databinding.ItemExerciseBinding
import sopt.motivoo.databinding.ItemExerciseNoticeBinding
import sopt.motivoo.domain.entity.exercise.ExerciseData.ExerciseItemInfo
import sopt.motivoo.presentation.exercise.ExerciseFragment.Companion.CHILD

class ExerciseEachDateInfoViewHolder(private val binding: ItemExerciseBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo, userType: String) {
        val context = binding.root.context
        initText(exerciseItemInfoData, binding, userType)
        initImage(exerciseItemInfoData, binding)
        checkStatus(exerciseItemInfoData, binding, context)
    }

    private fun initText(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        binding: ItemExerciseBinding,
        userType: String,
    ) {
        with(binding) {
            tvItemExerciseDate.text = exerciseItemInfoData.date
            tvItemExerciseImgBottomTxtLeft.text = exerciseItemInfoData.myMissionContent
            tvItemExerciseImgBottomTxtRight.text = exerciseItemInfoData.opponentMissionContent
            tvItemExerciseMyState.text = exerciseItemInfoData.myMissionStatus
            tvItemExerciseParentState.text = exerciseItemInfoData.opponentMissionStatus
            tvItemExerciseParentExercise.text =
                if (userType == CHILD) root.context.getString(R.string.exercise_parent_exercise) else root.context.getString(
                    R.string.exercise_child_exercise
                )
        }
    }

    private fun initImage(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        binding: ItemExerciseBinding,
    ) {
        with(binding) {
            if (exerciseItemInfoData.myMissionImgUrl != null) {
                ivItemExerciseLeftImage.load(exerciseItemInfoData.myMissionImgUrl)
            } else if (exerciseItemInfoData.myMissionStatus == "없음") {
                ivItemExerciseLeftImage.setImageResource(R.drawable.img_choose_exercise)
            } else {
                ivItemExerciseLeftImage.setImageResource(R.drawable.img_success_next_exercise)
            }
            if (exerciseItemInfoData.opponentMissionImgUrl != null) {
                ivItemExerciseRightImage.load(exerciseItemInfoData.opponentMissionImgUrl)
            } else if (exerciseItemInfoData.opponentMissionStatus == "없음") {
                ivItemExerciseRightImage.setImageResource(R.drawable.img_choose_exercise)
            } else {
                ivItemExerciseRightImage.setImageResource(R.drawable.img_success_next_exercise)
            }
        }
    }

    private fun checkStatus(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        binding: ItemExerciseBinding,
        context: Context,
    ) {
        when (exerciseItemInfoData.myMissionStatus) {
            STATE_SUCCESS_TYPE -> {
                binding.tvItemExerciseMyState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.blue_100_D7F6FF)
                val textColorBlue: Int = ContextCompat.getColor(context, R.color.blue_600_2E9ABB)
                binding.tvItemExerciseMyState.setTextColor(textColorBlue)
            }

            else -> {
                binding.tvItemExerciseMyState.visibility = View.GONE
            }
        }
        when (exerciseItemInfoData.opponentMissionStatus) {
            STATE_SUCCESS_TYPE -> {
                binding.tvItemExerciseParentState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.blue_100_D7F6FF)
                val textColorBlue: Int = ContextCompat.getColor(context, R.color.blue_600_2E9ABB)
                binding.tvItemExerciseParentState.setTextColor(textColorBlue)
            }

            else -> {
                binding.tvItemExerciseParentState.visibility = View.GONE
            }
        }
    }

    companion object {
        const val STATE_SUCCESS_TYPE = "성공"
    }
}

class ExerciseNoticeViewHolder(private val binding: ItemExerciseNoticeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(exerciseNoticeData: ExerciseItemInfo.NoticeItemInfo) {
        binding.tvExerciseTodayMission.text = exerciseNoticeData.missionContent
    }
}
