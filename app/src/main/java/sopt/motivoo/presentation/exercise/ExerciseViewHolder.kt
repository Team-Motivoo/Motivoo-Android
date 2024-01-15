package sopt.motivoo.presentation.exercise

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import sopt.motivoo.R
import sopt.motivoo.databinding.ItemExerciseBinding
import sopt.motivoo.databinding.ItemExerciseNoticeBinding
import sopt.motivoo.domain.entity.ExerciseInfo
import sopt.motivoo.util.extension.setVisible
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExerciseEachDateInfoViewHolder(private val binding: ItemExerciseBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(exerciseInfoData: ExerciseInfo.EachDateInfo) {
        with(binding) {
            val context = binding.root.context
            changePosition(exerciseInfoData, binding, context)
            initText(exerciseInfoData, binding)
            initImage(exerciseInfoData, binding)
            imageVisibility(exerciseInfoData, binding)
            checkStatus(exerciseInfoData, binding, context)
            compareDate(binding, context)
        }
    }

    private fun changePosition(
        exerciseInfoData: ExerciseInfo.EachDateInfo,
        binding: ItemExerciseBinding,
        context: Context,
    ) {
        if (exerciseInfoData.userType == "PARENT") {
            binding.tvItemExerciseMyExercise.text = context.getString(R.string.exercise_my_exercise)
            binding.tvItemExerciseParentExercise.text =
                context.getString(R.string.exercise_child_exercise)
        }
    }

    private fun initText(
        exerciseInfoData: ExerciseInfo.EachDateInfo,
        binding: ItemExerciseBinding,
    ) {
        with(binding) {
            tvItemExerciseDate.text = exerciseInfoData.date
            tvItemExerciseImgBottomTxtLeft.text = exerciseInfoData.myMissionContent
            tvItemExerciseImgBottomTxtRight.text = exerciseInfoData.opponentMissionContent
            tvItemExerciseMyState.text = exerciseInfoData.myMissionStatus
            tvItemExerciseParentState.text = exerciseInfoData.opponentMissionStatus
        }
    }

    private fun initImage(
        exerciseInfoData: ExerciseInfo.EachDateInfo,
        binding: ItemExerciseBinding,
    ) {
        with(binding) {
            ivItemExerciseFinishLeft.load(exerciseInfoData.myMissionImgUrl)
            ivItemExerciseFinishRight.load(exerciseInfoData.opponentMissionImgUrl)
        }
    }

    private fun imageVisibility(
        exerciseInfoData: ExerciseInfo.EachDateInfo,
        binding: ItemExerciseBinding,
    ) {
        if (exerciseInfoData.myMissionImgUrl == null) {
            binding.ivItemExerciseFinishLeft.setVisible(View.INVISIBLE)
            binding.tvItemExerciseNoImageBeforeExerciseLeft.setVisible(View.VISIBLE)
        } else {
            binding.ivItemExerciseFinishLeft.setVisible(View.VISIBLE)
            binding.tvItemExerciseNoImageBeforeExerciseLeft.setVisible(View.INVISIBLE)
        }
        if (exerciseInfoData.opponentMissionImgUrl == null) {
            binding.ivItemExerciseFinishRight.setVisible(View.INVISIBLE)
            binding.tvItemExerciseNoImageBeforeExerciseRight.setVisible(View.VISIBLE)
        } else {
            binding.ivItemExerciseFinishRight.setVisible(View.VISIBLE)
            binding.tvItemExerciseNoImageBeforeExerciseRight.setVisible(View.INVISIBLE)
        }
    }

    private fun checkStatus(
        exerciseInfoData: ExerciseInfo.EachDateInfo,
        binding: ItemExerciseBinding,
        context: Context,
    ) {
        when (exerciseInfoData.myMissionStatus) {
            STATE_EXERCISING_TYPE -> {
                binding.tvItemExerciseMyState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.pink_100_FFE6F5)
                val textColorPink: Int = ContextCompat.getColor(context, R.color.pink_FF19A3)
                binding.tvItemExerciseMyState.setTextColor(textColorPink)
            }

            STATE_FINISH_TYPE -> {
                binding.tvItemExerciseMyState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.blue_100_D7F6FF)
                val textColorBlue: Int = ContextCompat.getColor(context, R.color.blue_600_2E9ABB)
                binding.tvItemExerciseMyState.setTextColor(textColorBlue)
            }

            else -> {
                binding.tvItemExerciseMyState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.gray_300_D5D5D7)
                val textColorGray: Int = ContextCompat.getColor(context, R.color.gray_700_464747)
                binding.tvItemExerciseMyState.setTextColor(textColorGray)
            }
        }
        when (exerciseInfoData.opponentMissionStatus) {
            STATE_EXERCISING_TYPE -> {
                binding.tvItemExerciseParentState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.pink_100_FFE6F5)
                val textColorPink: Int = ContextCompat.getColor(context, R.color.pink_FF19A3)
                binding.tvItemExerciseParentState.setTextColor(textColorPink)
            }

            STATE_FINISH_TYPE -> {
                binding.tvItemExerciseParentState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.blue_100_D7F6FF)
                val textColorBlue: Int = ContextCompat.getColor(context, R.color.blue_600_2E9ABB)
                binding.tvItemExerciseParentState.setTextColor(textColorBlue)
            }

            else -> {
                binding.tvItemExerciseParentState.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.gray_300_D5D5D7)
                val textColorGray: Int = ContextCompat.getColor(context, R.color.gray_700_464747)
                binding.tvItemExerciseParentState.setTextColor(textColorGray)
            }
        }
    }

    private fun compareDate(binding: ItemExerciseBinding, context: Context) {
        val currentDate = getCurrentDate()
        if (currentDate == binding.tvItemExerciseDate.text.substring(0, 13)) {
            binding.tvItemExerciseNoImageBeforeExerciseLeft.text =
                context.getString(R.string.exercise_no_image_before_exercise_today)
            binding.tvItemExerciseNoImageBeforeExerciseRight.text =
                context.getString(R.string.exercise_no_image_before_exercise_today)
        }
    }

    private fun getCurrentDate(): String {
        val dataFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
        val currentDate = Date()
        return dataFormat.format(currentDate)
    }

    companion object {
        const val STATE_EXERCISING_TYPE = "진행중"
        const val STATE_FINISH_TYPE = "성공"
    }
}

class ExerciseNoticeViewHolder(private val binding: ItemExerciseNoticeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(exerciseNoticeData: ExerciseInfo.NoticeInfo) {
        binding.tvExerciseTodayMission.text = exerciseNoticeData.missionContent
    }
}
