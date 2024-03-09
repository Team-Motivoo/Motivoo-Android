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
import sopt.motivoo.util.extension.prettyString
import sopt.motivoo.util.extension.setVisible
import java.time.LocalDate

class ExerciseEachDateInfoViewHolder(private val binding: ItemExerciseBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo, userType: String) {
        val context = binding.root.context
        initText(exerciseItemInfoData, binding, userType)
        initImage(exerciseItemInfoData, binding)
        imageVisibility(exerciseItemInfoData, binding)
        checkStatus(exerciseItemInfoData, binding, context)
        compareDate(binding, context)
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
            ivItemExerciseFinishLeft.load(exerciseItemInfoData.myMissionImgUrl)
            if (exerciseItemInfoData.myMissionImgUrl == null) {
                ivItemExerciseFinishLeft.visibility = View.GONE
            }
            if (exerciseItemInfoData.opponentMissionImgUrl == null) {
                ivItemExerciseFinishRight.visibility = View.GONE
            }
        }
    }

    private fun imageVisibility(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        binding: ItemExerciseBinding,
    ) {
        if (exerciseItemInfoData.myMissionImgUrl == null) {
            binding.ivItemExerciseFinishLeft.setVisible(View.INVISIBLE)
            binding.tvItemExerciseNoImageBeforeExerciseLeft.setVisible(View.VISIBLE)
        } else {
            binding.ivItemExerciseFinishLeft.setVisible(View.VISIBLE)
            binding.tvItemExerciseNoImageBeforeExerciseLeft.setVisible(View.INVISIBLE)
        }
        if (exerciseItemInfoData.opponentMissionImgUrl == null) {
            binding.ivItemExerciseFinishRight.setVisible(View.INVISIBLE)
            binding.tvItemExerciseNoImageBeforeExerciseRight.setVisible(View.VISIBLE)
        } else {
            binding.ivItemExerciseFinishRight.setVisible(View.VISIBLE)
            binding.tvItemExerciseNoImageBeforeExerciseRight.setVisible(View.INVISIBLE)
        }
    }

    private fun checkStatus(
        exerciseItemInfoData: ExerciseItemInfo.EachDateItemInfo,
        binding: ItemExerciseBinding,
        context: Context,
    ) {
        when (exerciseItemInfoData.myMissionStatus) {
            STATE_EXERCISING_TYPE -> {
                binding.tvItemExerciseMyState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.pink_100_FFE6F5)
                val textColorPink: Int = ContextCompat.getColor(context, R.color.pink_FF19A3)
                binding.tvItemExerciseMyState.setTextColor(textColorPink)
            }

            STATE_SUCCESS_TYPE -> {
                binding.tvItemExerciseMyState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.blue_100_D7F6FF)
                val textColorBlue: Int = ContextCompat.getColor(context, R.color.blue_600_2E9ABB)
                binding.tvItemExerciseMyState.setTextColor(textColorBlue)
            }

            STATE_FAILURE_TYPE -> {
                binding.tvItemExerciseMyState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.gray_300_D5D5D7)
                val textColorGray: Int = ContextCompat.getColor(context, R.color.gray_700_464747)
                binding.tvItemExerciseMyState.setTextColor(textColorGray)
            }

            else -> {
                binding.tvItemExerciseMyState.visibility = View.GONE
            }
        }
        when (exerciseItemInfoData.opponentMissionStatus) {
            STATE_EXERCISING_TYPE -> {
                binding.tvItemExerciseParentState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.pink_100_FFE6F5)
                val textColorPink: Int = ContextCompat.getColor(context, R.color.pink_FF19A3)
                binding.tvItemExerciseParentState.setTextColor(textColorPink)
            }

            STATE_SUCCESS_TYPE -> {
                binding.tvItemExerciseParentState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.blue_100_D7F6FF)
                val textColorBlue: Int = ContextCompat.getColor(context, R.color.blue_600_2E9ABB)
                binding.tvItemExerciseParentState.setTextColor(textColorBlue)
            }

            STATE_FAILURE_TYPE -> {
                binding.tvItemExerciseParentState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.gray_300_D5D5D7)
                val textColorGray: Int = ContextCompat.getColor(context, R.color.gray_700_464747)
                binding.tvItemExerciseParentState.setTextColor(textColorGray)
            }

            else -> {
                binding.tvItemExerciseParentState.visibility = View.GONE
            }
        }
    }

    private fun compareDate(binding: ItemExerciseBinding, context: Context) {
        fun String.isToday(): Boolean = this == LocalDate.now().prettyString
        fun String.removeDayOfTheWeek(): String = this.removeRange(length - 4 until length)

        if (binding.tvItemExerciseDate.text.toString().removeDayOfTheWeek().isToday()) {
            binding.tvItemExerciseNoImageBeforeExerciseLeft.text =
                context.getString(R.string.exercise_no_image_before_exercise_today)
            binding.tvItemExerciseNoImageBeforeExerciseRight.text =
                context.getString(R.string.exercise_no_image_before_exercise_today)

            binding.ivItemExerciseNoImageBeforeExerciseLeft.visibility = View.GONE
            binding.ivItemExerciseNoImageBeforeExerciseRight.visibility = View.GONE
        } else {
            binding.tvItemExerciseNoImageBeforeExerciseLeft.text =
                context.getString(R.string.exercise_no_image_before_exercise)
            binding.tvItemExerciseNoImageBeforeExerciseRight.text =
                context.getString(R.string.exercise_no_image_before_exercise)
        }
    }

    companion object {
        const val STATE_EXERCISING_TYPE = "진행중"
        const val STATE_SUCCESS_TYPE = "성공"
        const val STATE_FAILURE_TYPE = "실패"
    }
}

class ExerciseNoticeViewHolder(private val binding: ItemExerciseNoticeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(exerciseNoticeData: ExerciseItemInfo.NoticeItemInfo) {
        binding.tvExerciseTodayMission.text = exerciseNoticeData.missionContent
    }
}
